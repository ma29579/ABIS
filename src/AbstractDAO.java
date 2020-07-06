import java.sql.*;
import java.util.HashMap;

public abstract class AbstractDAO <T> {

    private HashMap<Long, T> cache = new HashMap<Long, T>();
    protected Connection db;
    protected ResultSet rs;

    public AbstractDAO(){
        try {

            db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/abis", "postgres", "postgres");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected abstract String findStatement();
    protected abstract String deleteStatement();
    protected abstract String updateStatement();
    protected abstract String whereStatement();
    protected abstract String insertStatement();
    protected abstract Long getKey(T object);
    protected abstract Connection getConnection();
    protected abstract void doUpdate();
    protected abstract T doLoad(Long id, ResultSet rs);
    protected abstract long doInsert(T object) throws SQLException;

    protected long create(T object) throws SQLException {
        long id = getKey(object);

        if(cache.containsKey(id))
            throw new SQLException(("Schlüssel " + id + " bei Insert schon in DB enthalten!"));

        id = doInsert(object);
        cache.put(id, object);
        return id;
    }

    protected abstract void update(T object) throws SQLException;

    protected void cleanUp(){
        cache.clear();
    }

    protected T load(T object, Long id) throws SQLException {
        cache.put(id,object);
        return object;
    }

    protected ResultSet abstractMultipleRead() throws SQLException{
        PreparedStatement whereStatement = null;
        Connection db = getConnection();
        whereStatement = db.prepareStatement(whereStatement());
        rs = whereStatement.executeQuery();

        return rs;
    }

    protected T abstractSingleRead(Long id) throws SQLException {
        T result = cache.get(id);
        if(result != null)
            return result;
        PreparedStatement findStatement = null;
        Connection db = getConnection();
        findStatement = db.prepareStatement(findStatement());
        findStatement.setLong(1, id.longValue());
        rs = findStatement.executeQuery();

        if(!rs.next())
            return null;

        result = doLoad(id, rs);
        return result;
    }

    protected void delete(T Object, Long id) throws SQLException{
        PreparedStatement deleteStatement = null;

        deleteStatement = db.prepareStatement(deleteStatement());
        deleteStatement.setLong(1,id);
        deleteStatement.execute();
        cache.remove(id);
    }

}
