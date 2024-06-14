package com.finobank.accounts.adapter.database.config;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.UUID;

public class CustomUuidArrayType implements UserType<UUID[]> {
    @Override
    public int getSqlType() {
        return Types.ARRAY;
    }

    @Override
    public Class<UUID[]> returnedClass() {
        return UUID[].class;
    }

    @Override
    public boolean equals(UUID[] x, UUID[] y) {
        return Arrays.equals(x, y);
    }

    @Override
    public int hashCode(UUID[] x) {
        return Arrays.hashCode(x);
    }

    @Override
    public UUID[] nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        Array array = rs.getArray(position);
        if (array == null) {
            return null;
        }
        return (UUID[]) array.getArray();
    }

    @Override
    public void nullSafeSet(PreparedStatement st, UUID[] value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.ARRAY);
        } else {
            Connection connection = st.getConnection();
            Array array = connection.createArrayOf("uuid", value);
            st.setArray(index, array);
        }
    }

    @Override
    public UUID[] deepCopy(UUID[] value) {
        return value == null ? null : value.clone();
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(UUID[] value) {
        return deepCopy(value);
    }

    @Override
    public UUID[] assemble(Serializable cached, Object owner) {
        return deepCopy((UUID[]) cached);
    }

    @Override
    public UUID[] replace(UUID[] original, UUID[] target, Object owner) {
        return deepCopy(original);
    }
}
