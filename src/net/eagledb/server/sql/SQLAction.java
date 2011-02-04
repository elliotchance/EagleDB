package net.eagledb.server.sql;

import java.sql.*;
import net.eagledb.server.*;

public interface SQLAction {

	public Result getResult() throws SQLException;

}
