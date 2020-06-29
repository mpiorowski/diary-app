package pbs.api.config.mybatis;

import com.google.common.base.Strings;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.UUID;

@MappedJdbcTypes(JdbcType.OTHER)
@MappedTypes(UUID.class)
public class UUIDTypeHandler implements TypeHandler<UUID> {

  private static final Logger logger = LoggerFactory.getLogger(UUIDTypeHandler.class);

  @Override
  public void setParameter(PreparedStatement ps, int i, UUID parameter, JdbcType jdbcType)
      throws SQLException {
    if (parameter == null) {
      ps.setObject(i, null, Types.OTHER);
    } else {
      ps.setObject(i, parameter.toString(), Types.OTHER);
    }
  }

  @Override
  public UUID getResult(ResultSet rs, String columnName) throws SQLException {
    return toUUID(rs.getString(columnName));
  }

  @Override
  public UUID getResult(ResultSet rs, int columnIndex) throws SQLException {
    return toUUID(rs.getString(columnIndex));
  }

  @Override
  public UUID getResult(CallableStatement cs, int columnIndex) throws SQLException {
    return toUUID(cs.getString(columnIndex));
  }

  private static UUID toUUID(String val) {
    if (Strings.isNullOrEmpty(val)) {
      return null;
    }
    try {
      return UUID.fromString(val);
    } catch (IllegalArgumentException e) {
      logger.warn("Bad UUID found: {}", val);
    }
    return null;
  }
}
