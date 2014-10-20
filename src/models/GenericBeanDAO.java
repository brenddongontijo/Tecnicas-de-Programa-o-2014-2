package models;

import android.database.SQLException;

import java.util.ArrayList;
import java.util.HashMap;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import libraries.DataBase;

/*
 * Class Name: GenericBeanDAO
 * This class aims to make a generic DAO and avoid replication code. 
 */
public class GenericBeanDAO extends DataBase {

	// Represents a statement that can be executed against a database.
	private SQLiteStatement pst;

	public GenericBeanDAO() throws SQLException {
		super();
	}

	// This method will access a specific table in Database and ordering if
	// necessary.
	public ArrayList<Bean> selectBeanRelationship(Bean bean, String table,
			String orderField) throws SQLException {
		this.openConnection();

		ArrayList<Bean> beans = new ArrayList<Bean>();

		String sql = "SELECT c.* FROM " + table + " as c, " + bean.relationship
				+ " as ci " + "WHERE ci.id_" + bean.identifier + "= ? "
				+ "AND ci.id_" + table + " = c._id GROUP BY c._id";

		if (orderField != null) {
			sql += " ORDER BY " + orderField;
		}

		Cursor cursorBeans = this.database.rawQuery(sql,
				new String[] { bean.get(bean.fieldsList().get(0)) });

		moveCursor(cursorBeans, beans, table);

		this.closeConnection();

		return beans;
	}

	public void moveCursor(Cursor cursorBeans, ArrayList<Bean> beans,
			String table) {

		while (cursorBeans.moveToNext()) {
			Bean object = init(table);

			for (String fieldString : object.fieldsList()) {
				object.set(fieldString, cursorBeans.getString(cursorBeans
						.getColumnIndex(fieldString)));
			}

			beans.add(object);
		}
	}

	// This method will access a specific table in Database based on year and
	// ordering if necessary.
	public ArrayList<Bean> selectBeanRelationship(Bean bean, String table,
			int year, String orderField) throws SQLException {
		this.openConnection();

		ArrayList<Bean> beans = new ArrayList<Bean>();

		String sql = "SELECT c.* FROM " + table + " as c, " + "evaluation"
				+ " as ci " + "WHERE ci.id_" + bean.identifier + "= ? "
				+ "AND ci.id_" + table
				+ " = c._id AND ci.year = ? GROUP BY c._id";

		if (orderField != null) {
			sql += " ORDER BY " + orderField;
		}

		Cursor cursorBeans = this.database.rawQuery(
				sql,
				new String[] { bean.get(bean.fieldsList().get(0)),
						Integer.toString(year) });

		moveCursor(cursorBeans, beans, table);

		this.closeConnection();

		return beans;
	}

	// TO-DO.
	public ArrayList<Bean> selectFromFields(Bean bean,
			ArrayList<String> fields, String orderField) throws SQLException {
		this.openConnection();

		ArrayList<Bean> beans = new ArrayList<Bean>();
		ArrayList<String> values = new ArrayList<String>();

		// String sql = "SELECT * FROM " + bean.identifier + " WHERE ";
		String sql = "";

		for (String s : fields) {
			sql += " " + s + " = ? AND";
			values.add(bean.get(s));
		}

		sql = sql.substring(0, sql.length() - 3);

		if (orderField != null) {
			sql += " ORDER BY " + orderField;
		}

		String[] strings = new String[values.size()];
		strings = values.toArray(strings);

		Cursor cs;

		cs = this.database.query(bean.identifier, null, sql, strings, null,
				null, null);

		while (cs.moveToNext()) {
			Bean object = init(bean.identifier);

			for (String s : object.fieldsList()) {
				object.set(s, cs.getString(cs.getColumnIndex(s)));
			}

			beans.add(object);
		}

		this.closeConnection();

		return beans;
	}

	// This method insert values in a specific Bean table.
	public boolean insertBean(Bean bean) throws SQLException {
		this.openConnection();

		String replace = "";
		int i = 1;

		ArrayList<String> notPrimaryFields = bean.fieldsList();

		// Removing id.
		notPrimaryFields.remove(0);

		String sql = "INSERT INTO " + bean.identifier + "(";

		for (String s : notPrimaryFields) {
			sql += s + ",";
			replace += "?,";
		}

		sql = sql.substring(0, sql.length() - 1);
		replace = replace.substring(0, replace.length() - 1);

		sql += ") VALUES(" + replace + ")";

		this.pst = this.database.compileStatement(sql);

		for (String s : notPrimaryFields) {
			this.pst.bindString(i, bean.get(s));
			i++;
		}

		long result = this.pst.executeInsert();
		this.pst.clearBindings();

		this.closeConnection();

		return (result != -1) ? true : false;
	}

	// This method adds a relation between Institution and Course.
	public boolean addBeanRelationship(Bean parentBean, Bean childBean)
			throws SQLException {
		this.openConnection();

		String sql = "INSERT INTO " + parentBean.relationship + "(id_"
				+ parentBean.identifier + ",id_" + childBean.identifier
				+ ") VALUES(?,?)";

		this.pst = this.database.compileStatement(sql);
		this.pst.bindString(1, parentBean.get(parentBean.fieldsList().get(0)));
		this.pst.bindString(2, childBean.get(childBean.fieldsList().get(0)));

		long result = this.pst.executeInsert();
		this.pst.clearBindings();

		this.closeConnection();

		return (result != -1) ? true : false;
	}

	// This method deletes the relation between Institution and Course.
	public boolean deleteBeanRelationship(Bean parentBean, Bean childBean)
			throws SQLException {
		this.openConnection();

		String sql = "DELETE FROM " + parentBean.relationship + "  WHERE id_"
				+ parentBean.identifier + " = ? AND id_" + childBean.identifier
				+ " = ?";

		this.pst = this.database.compileStatement(sql);
		this.pst.bindString(1, parentBean.get(parentBean.fieldsList().get(0)));
		this.pst.bindString(2, childBean.get(childBean.fieldsList().get(0)));

		int result = this.pst.executeUpdateDelete();
		this.pst.clearBindings();

		this.closeConnection();

		return (result == 1) ? true : false;
	}

	// This method will return a specific Bean with all theirs values in
	// Database.
	public Bean selectBean(Bean bean) throws SQLException {
		this.openConnection();

		Bean result = null;

		String sql = "SELECT * FROM " + bean.identifier + " WHERE "
				+ bean.fieldsList().get(0) + " = ?";

		Cursor cs = this.database.rawQuery(sql,
				new String[] { bean.get(bean.fieldsList().get(0)) });

		if (cs.moveToFirst()) {
			result = init(bean.identifier);

			for (String s : bean.fieldsList()) {
				result.set(s, cs.getString(cs.getColumnIndex(s)));
			}
		}

		this.closeConnection();

		return result;
	}

	// This method keeps all same Beans into a ArrayList.
	public ArrayList<Bean> selectAllBeans(Bean type, String orderField)
			throws SQLException {
		this.openConnection();

		ArrayList<Bean> beans = new ArrayList<Bean>();
		Cursor cs = this.database.query(type.identifier, null, null, null,
				null, null, orderField);

		while (cs.moveToNext()) {
			Bean bean = init(type.identifier);

			for (String s : type.fieldsList()) {
				bean.set(s, cs.getString(cs.getColumnIndex(s)));
			}

			beans.add(bean);
		}

		this.closeConnection();

		return beans;
	}

	public ArrayList<String[]> runSql(String sql) throws SQLException {
		this.openConnection();

		ArrayList<String[]> result = new ArrayList<String[]>();

		Cursor cs = this.database.rawQuery(sql, null);

		while (cs.moveToNext()) {
			String[] data = new String[cs.getColumnCount()];

			for (int i = 0; i < cs.getColumnCount(); i++)
				data[i] = cs.getString(i);

			result.add(data);
		}

		this.closeConnection();

		return result;
	}

	public ArrayList<Bean> runSql(Bean type, String sql) throws SQLException {
		this.openConnection();
		ArrayList<Bean> result = new ArrayList<Bean>();
		Cursor cs = this.database.rawQuery(sql, null);
		while (cs.moveToNext()) {
			Bean bean = init(type.identifier);
			for (String s : type.fieldsList()) {
				bean.set(s, cs.getString(cs.getColumnIndex(s)));
			}
			result.add(bean);
		}
		this.closeConnection();
		return result;
	}

	/*
	 * The method countBean() count the number of Beans on Database based on
	 * Bean identifier.
	 */
	public Integer countBean(Bean type) throws SQLException {
		this.openConnection();

		Integer count = 0;
		String sql = "SELECT * FROM " + type.identifier;
		Cursor cs = this.database.rawQuery(sql, null);

		if (cs.moveToFirst()) {
			count = cs.getCount();
		}

		this.closeConnection();

		return count;
	}

	/*
	 * The method firstOrLastBean() returns the first or last Bean based on
	 * boolean "last".
	 */
	public Bean firstOrLastBean(Bean type, boolean last) throws SQLException {
		Bean bean = null;
		String sql = "SELECT * FROM " + type.identifier + " ORDER BY "
				+ type.fieldsList().get(0);

		// Testing if last == false.
		if (!last) {
			sql += " LIMIT 1";
		} else {
			sql += " DESC LIMIT 1";
		}

		this.openConnection();
		Cursor cs = this.database.rawQuery(sql, null);

		if (cs.moveToFirst()) {
			bean = init(type.identifier);

			for (String s : type.fieldsList()) {
				bean.set(s, cs.getString(cs.getColumnIndex(s)));
			}
		}
		this.closeConnection();

		return bean;
	}

	public ArrayList<HashMap<String, String>> selectOrdered(
			ArrayList<String> returnFields, String orderedBy, String condition,
			String groupBy, boolean desc) {
		String fields = "";

		for (String s : returnFields) {
			fields += s + ",";
		}

		fields = fields.substring(0, fields.length() - 1);
		String sql = "SELECT "
				+ fields
				+ " FROM course AS c,institution AS i , evaluation AS e, articles AS a, books AS b"
				+ " WHERE id_institution = i._id AND id_course = c._id AND id_articles = a._id AND id_books = b._id ";

		if (condition != null) {
			sql += "AND " + condition + " ";
		}
		if (groupBy != null) {
			sql += " GROUP BY " + groupBy;
		}
		if (orderedBy != null) {
			sql += " ORDER BY " + orderedBy;
		}
		if (desc) {
			sql += " DESC";
		} else {
			sql += " ASC";
		}

		ArrayList<HashMap<String, String>> values = new ArrayList<HashMap<String, String>>();
		this.openConnection();
		Cursor cs = this.database.rawQuery(sql, null);

		while (cs.moveToNext()) {
			HashMap<String, String> hash = new HashMap<String, String>();
			for (String s : returnFields) {
				hash.put(s, cs.getString(cs.getColumnIndex(s)));
			}
			hash.put("order_field", orderedBy);
			values.add(hash);
		}

		this.closeConnection();
		return values;
	}

	public ArrayList<Bean> selectBeanWhere(Bean type, String field,
			String value, boolean use_like, String orderField)
			throws SQLException {
		this.openConnection();

		ArrayList<Bean> beans = new ArrayList<Bean>();
		Cursor cs;

		// Testing is user_like == false.
		if (!use_like) {

			// Making a search on database based on "value".
			cs = this.database.query(type.identifier, null, field + " = ?",
					new String[] { value }, null, null, orderField);
		} else {
			cs = this.database.query(type.identifier, null, field + " LIKE ?",
					new String[] { "%" + value + "%" }, null, null, orderField);
		}

		// Getting all beans and adding into ArrayList.
		while (cs.moveToNext()) {
			Bean bean = init(type.identifier);

			for (String s : type.fieldsList()) {
				bean.set(s, cs.getString(cs.getColumnIndex(s)));
			}

			beans.add(bean);
		}
		this.closeConnection();

		return beans;
	}

	// The method deleteBean() aims to delete a determinate Bean on Database.
	public boolean deleteBean(Bean bean) throws SQLException {
		this.openConnection();

		// bean.fieldsList().get(0) is the primary key from bean.identifier.
		String sql = "DELETE FROM " + bean.identifier + " WHERE "
				+ bean.fieldsList().get(0) + " = ?";
		this.pst = this.database.compileStatement(sql);
		this.pst.bindString(1, bean.get(bean.fieldsList().get(0)));
		int result = this.pst.executeUpdateDelete();
		this.pst.clearBindings();
		this.closeConnection();

		return (result == 1) ? true : false;
	}

	// The method init() Creates a Bean based on the current bean.Identifier.
	public Bean init(String beanIdentifier) {
		Bean object = null;

		if (beanIdentifier.equals("institution")) {
			object = new Institution();
		} else if (beanIdentifier.equals("course")) {
			object = new Course();
		} else if (beanIdentifier.equals("books")) {
			object = new Book();
		} else if (beanIdentifier.equals("articles")) {
			object = new Article();
		} else if (beanIdentifier.equals("evaluation")) {
			object = new Evaluation();
		} else if (beanIdentifier.equals("search")) {
			object = new Search();
		}

		return object;
	}
}
