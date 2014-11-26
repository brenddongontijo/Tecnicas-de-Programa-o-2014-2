package models;

import android.database.SQLException;

import java.util.ArrayList;
import java.util.HashMap;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import libraries.DataBase;

/**
 * Class Name: GenericBeanDAO
 * 
 * This class aims to make a generic DAO and avoid replication code. 
 */
public class GenericBeanDAO extends DataBase {

	// Represents a statement that can be executed against a database.
	private SQLiteStatement performDatabase;

	/**
	 * @throws SQLException
	 */
	public GenericBeanDAO() throws SQLException {
		super();
	}

	/**
	 * This method will access a specific table in Database and ordering if
	 * necessary.
	 * 
	 * @param bean
	 * @param table
	 * @param orderField
	 * @return beans.
	 * @throws SQLException
	 */
	public ArrayList<Bean> selectBeanRelationship(Bean bean, String table,
			String orderField) throws SQLException {
		this.openConnection();

		ArrayList<Bean> beans = new ArrayList<Bean>();

		String sql = "SELECT c.* FROM " + table + " as c, " + bean.relationship
				+ " as ci " + "WHERE ci.id_" + bean.identifier + "= ? "
				+ "AND ci.id_" + table + " = c._id GROUP BY c._id";

		if (orderField != null) {
			sql = sql + " ORDER BY " + orderField;
		}

		Cursor cursorBeans = this.database.rawQuery(sql,
				new String[] { bean.get(bean.fieldsList().get(0)) });

		insertBeans(cursorBeans, beans, table);

		this.closeConnection();

		return beans;
	}
	
	/**
	 * @param cursorBeans
	 * @param beans
	 * @param table
	 */
	public void insertBeans(Cursor cursorBeans, ArrayList<Bean> beans,
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

	/**
	 * This method will access a specific table in Database based on year and
	 * ordering if necessary.
	 * 
	 * @param bean
	 * @param table
	 * @param year
	 * @param orderField
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Bean> selectBeanRelationship(Bean bean, String table,
			int year, String orderField) throws SQLException {
		this.openConnection();

		ArrayList<Bean> beans = new ArrayList<Bean>();

		String sql = "SELECT c.* FROM " + table + " as c, " + "evaluation"
				+ " as ci " + "WHERE ci.id_" + bean.identifier + "= ? "
				+ "AND ci.id_" + table
				+ " = c._id AND ci.year = ? GROUP BY c._id";

		if (orderField != null) {
			sql = sql + " ORDER BY " + orderField;
		}

		Cursor cursorBeans = this.database.rawQuery(
				sql, new String[] { bean.get(bean.fieldsList().get(0)),
						Integer.toString(year) });

		insertBeans(cursorBeans, beans, table);

		this.closeConnection();

		return beans;
	}

	/**
	 * @param bean
	 * @param fields
	 * @param orderField
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Bean> selectFromFields(Bean bean,
			ArrayList<String> fields, String orderField) throws SQLException {
		this.openConnection();

		ArrayList<Bean> beans = new ArrayList<Bean>();
		ArrayList<String> values = new ArrayList<String>();
		
		//String sql = "SELECT * FROM " + bean.identifier + " WHERE ";
		String sql = "";

		for (String beanName : fields) {
			//sql = sql + " " + s + " = ? AND";
			sql += " " + beanName + " = ? AND";
			values.add(bean.get(beanName));
		}
		
		sql = sql.substring(0, sql.length() - 3);

		if (orderField != null) {
			sql = sql + " ORDER BY " + orderField;
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

	/**
	 * This method insert values in a specific Bean table.
	 * 
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public boolean insertBean(Bean bean) throws SQLException {
		this.openConnection();

		String replace = "";
		int i = 1;

		ArrayList<String> notPrimaryFields = bean.fieldsList();

		// Removing id.
		notPrimaryFields.remove(0);

		String sql = "INSERT INTO " + bean.identifier + "(";

		for (String s : notPrimaryFields) {
			sql = sql + s + ",";
			replace += "?,";
		}

		sql = sql.substring(0, sql.length() - 1);
		replace = replace.substring(0, replace.length() - 1);

		sql = sql + ") VALUES(" + replace + ")";

		this.performDatabase = this.database.compileStatement(sql);

		for (String s : notPrimaryFields) {
			this.performDatabase.bindString(i, bean.get(s));
			i = i + 1;
		}

		long result = this.performDatabase.executeInsert();
		this.performDatabase.clearBindings();

		this.closeConnection();
		
		if (result != -1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method adds a relation between Institution and Course.
	 * 
	 * @param parentBean
	 * @param childBean
	 * @return
	 * @throws SQLException
	 */
	public boolean addBeanRelationship(Bean parentBean, Bean childBean)
			throws SQLException {
		this.openConnection();

		String sql = "INSERT INTO " + parentBean.relationship + "(id_"
				+ parentBean.identifier + ",id_" + childBean.identifier
				+ ") VALUES(?,?)";

		this.performDatabase = this.database.compileStatement(sql);
		this.performDatabase.bindString(1, parentBean.get(parentBean.fieldsList().get(0)));
		this.performDatabase.bindString(2, childBean.get(childBean.fieldsList().get(0)));

		long result = this.performDatabase.executeInsert();
		this.performDatabase.clearBindings();

		this.closeConnection();
		
		if (result != -1) {
			return true;
		} else {
			return false;
		}
		
	}

	/**
	 * This method deletes the relation between Institution and Course.
	 * 
	 * @param parentBean
	 * @param childBean
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteBeanRelationship(Bean parentBean, Bean childBean)
			throws SQLException {
		this.openConnection();

		String sql = "DELETE FROM " + parentBean.relationship + "  WHERE id_"
				+ parentBean.identifier + " = ? AND id_" + childBean.identifier
				+ " = ?";

		this.performDatabase = this.database.compileStatement(sql);
		this.performDatabase.bindString(1, parentBean.get(parentBean.fieldsList().get(0)));
		this.performDatabase.bindString(2, childBean.get(childBean.fieldsList().get(0)));

		int result = this.performDatabase.executeUpdateDelete();
		this.performDatabase.clearBindings();

		this.closeConnection();
		
		boolean deleteSucefull = (result == 1);
		
		if (deleteSucefull) {
			return true;
		} 
		else {
			return false;
		}
	}

	/**
	 * This method will return a specific Bean with all theirs values in
	 * Database.
	 * 
	 * @param choosenBean				chosen bean to be searched.	
	 * @return							chosen bean.
	 * @throws SQLException
	 */
	public Bean selectBean(Bean choosenBean) throws SQLException {
		this.openConnection();

		Bean result = null;

		String sql = "SELECT * FROM " + choosenBean.identifier + " WHERE "
				+ choosenBean.fieldsList().get(0) + " = ?";

		Cursor cs = this.database.rawQuery(sql,
				new String[] { choosenBean.get(choosenBean.fieldsList().get(0)) });

		if (cs.moveToFirst()) {
			result = init(choosenBean.identifier);

			for (String s : choosenBean.fieldsList()) {
				result.set(s, cs.getString(cs.getColumnIndex(s)));
			}
		}

		this.closeConnection();

		return result;
	}

	/**
	 * This method keeps all same Beans into a ArrayList.
	 * 
	 * @param type
	 * @param orderField
	 * @return
	 * @throws SQLException
	 */
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

	/**
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
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

	/**
	 * @param beanCursor
	 * @param listOfBeans
	 * @param typeBean
	 */
	public void setBeanOfIdentifier(Cursor beanCursor, ArrayList<Bean> listOfBeans, Bean typeBean ){
		while (beanCursor.moveToNext()) {
			Bean bean = init(typeBean.identifier);
			for (String nameBeanField : typeBean.fieldsList()) {
				bean.set(nameBeanField, beanCursor.getString(beanCursor.getColumnIndex(nameBeanField)));
			}
			listOfBeans.add(bean);
		}
	}
	
	/**
	 * @param typeBean
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Bean> runSql(Bean typeBean, String sql) throws SQLException {
		this.openConnection();
		ArrayList<Bean> listOfBeans = new ArrayList<Bean>();
		Cursor beanCursor = this.database.rawQuery(sql, null);

		setBeanOfIdentifier(beanCursor, listOfBeans, typeBean);
		this.closeConnection();
		return listOfBeans;
	}

	/**
	 * The method countBean() count the number of Beans on Database based on
	 * Bean identifier.
	 * 
	 * @param type
	 * @return
	 * @throws SQLException
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

	/**
	 * The method firstOrLastBean() returns the first or last Bean based on
	 * boolean "last".
	 * 
	 * @param type
	 * @param last
	 * @return
	 * @throws SQLException
	 */
	public Bean firstOrLastBean(Bean type, boolean last) throws SQLException {
		Bean bean = null;
		String sql = "SELECT * FROM " + type.identifier + " ORDER BY "
				+ type.fieldsList().get(0);

		// Testing if last == false.
		if (!last) {
			sql = sql + " LIMIT 1";
		} else {
			sql = sql + " DESC LIMIT 1";
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

	/**
	 * @param returnFields
	 * @param orderedBy
	 * @param condition
	 * @param groupBy
	 * @param desc
	 * @return
	 */
	public ArrayList<HashMap<String, String>> selectOrdered(
			ArrayList<String> returnFields, String orderedBy, String condition,
			String groupBy, boolean desc) {
		String fields = "";

		for (String s : returnFields) {
			fields = fields + s + ",";
		}

		fields = fields.substring(0, fields.length() - 1);
		String sql = "SELECT "
				+ fields
				+ " FROM course AS c,institution AS i , evaluation AS e, articles AS a, books AS b"
				+ " WHERE id_institution = i._id AND id_course = c._id AND id_articles = a._id AND " +
				"id_books = b._id ";

		if (condition != null) {
			sql = sql + "AND " + condition + " ";
		}
		if (groupBy != null) {
			sql = sql + " GROUP BY " + groupBy;
		}
		if (orderedBy != null) {
			sql = sql + " ORDER BY " + orderedBy;
		}
		if (desc) {
			sql = sql + " DESC";
		} else {
			sql = sql + " ASC";
		}

		ArrayList<HashMap<String, String>> values = new ArrayList<HashMap<String, String>>();
		this.openConnection();
		Cursor databaseCursor = this.database.rawQuery(sql, null);

		while (databaseCursor.moveToNext()) {
			HashMap<String, String> hash = new HashMap<String, String>();
			for (String s : returnFields) {
				hash.put(s, databaseCursor.getString(databaseCursor.getColumnIndex(s)));
			}
			hash.put("order_field", orderedBy);
			values.add(hash);
		}

		this.closeConnection();
		return values;
	}

	/**
	 * @param typeOfBean
	 * @param fieldDatabase
	 * @param value
	 * @param use_like
	 * @param orderField
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Bean> selectBeanWhere(Bean typeOfBean, String fieldDatabase,
			String value, boolean use_like, String orderField)
			throws SQLException {
		this.openConnection();

		ArrayList<Bean> listBeans = new ArrayList<Bean>();
		Cursor makeSearch;

		// Testing is user_like == false.
		if (!use_like) {

			// Making a search on database based on "value".
			makeSearch = this.database.query(typeOfBean.identifier, null, fieldDatabase + " = ?",
					new String[] { value }, null, null, orderField);
		} else {
			makeSearch = this.database.query(typeOfBean.identifier, null, fieldDatabase + " LIKE ?",
					new String[] { "%" + value + "%" }, null, null, orderField);
		}

		// Getting all beans and adding into ArrayList.
		setBeanOfIdentifier(makeSearch, listBeans, typeOfBean);
		this.closeConnection();

		return listBeans;
	}

	/**
	 * The method deleteBean() aims to delete a determinate Bean on Database.
	 * 
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteBean(Bean bean) throws SQLException {
		this.openConnection();

		// bean.fieldsList().get(0) is the primary key from bean.identifier.
		String sql = "DELETE FROM " + bean.identifier + " WHERE "
				+ bean.fieldsList().get(0) + " = ?";
		this.performDatabase = this.database.compileStatement(sql);
		this.performDatabase.bindString(1, bean.get(bean.fieldsList().get(0)));
		int result = this.performDatabase.executeUpdateDelete();
		this.performDatabase.clearBindings();
		this.closeConnection();

		if (result == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * The method init() Creates a Bean based on the current bean.Identifier.
	 * 
	 * @param beanIdentifier
	 * @return
	 */
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
