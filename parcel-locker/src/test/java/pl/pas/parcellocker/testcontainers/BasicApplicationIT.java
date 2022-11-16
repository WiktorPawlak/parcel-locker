package pl.pas.parcellocker.testcontainers;

import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.pas.parcellocker.config.JakartaContainerInitializer;

import static org.apache.commons.lang3.BooleanUtils.isTrue;

@Testcontainers
public class BasicApplicationIT extends JakartaContainerInitializer {

  @Test
  public void checkContainerIsRunning(){
      assert(isTrue(jakartaApp.isRunning()));
  }
}
