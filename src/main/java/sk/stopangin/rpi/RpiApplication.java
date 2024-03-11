package sk.stopangin.rpi;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RpiApplication implements ApplicationRunner {

  public static void main(String[] args) {
    SpringApplication.run(RpiApplication.class, args);
  }

  private DataReader dataReader;

  @Autowired
  public void setDataReader(DataReader dataReader) {
    this.dataReader = dataReader;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    dataReader.readData();
  }
}
