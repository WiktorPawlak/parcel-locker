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
            .addContactPoint(new InetSocketAddress("127.22.0.2", 9042))
            .addContactPoint(new InetSocketAddress("127.22.0.3", 9043))
            .withLocalDatacenter("dc1")
            .withAuthCredentials("user", "password")
            .build()) {                                  // (1)
            ResultSet rs = session.execute("select release_version from system.local");              // (2)
            Row row = rs.one();
            System.out.println("Dupa" + row.getString("release_version"));                                    // (3)
        }
    }
}
