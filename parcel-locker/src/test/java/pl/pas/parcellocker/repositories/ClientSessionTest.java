package pl.pas.parcellocker.repositories;//package pl.pas.parcellocker.repositories;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

class ClientSessionTest {

    @Test
    void test() {
        try (CqlSession session = CqlSession.builder()
            .addContactPoint(new InetSocketAddress("1.2.3.4", 9042))
            .addContactPoint(new InetSocketAddress("5.6.7.8", 9043))
            .withLocalDatacenter("dc1")
            .withAuthCredentials("admin", "admin")
            .build()) {                                  // (1)
            ResultSet rs = session.execute("select release_version from system.local");              // (2)
            Row row = rs.one();
            System.out.println(row.getString("release_version"));                                    // (3)
        }
    }
}
