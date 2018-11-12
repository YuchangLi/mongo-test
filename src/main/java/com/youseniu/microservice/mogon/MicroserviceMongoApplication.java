package com.youseniu.microservice.mogon;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@SpringBootApplication
public class MicroserviceMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceMongoApplication.class, args);
	}
}

@Configuration
@EnableMongoRepositories
class ApplicationConfig extends AbstractMongoConfiguration {

  @Override
  public Mongo mongo() throws Exception {
    //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址  
    //ServerAddress()两个参数分别为 服务器地址 和 端口  
    ServerAddress serverAddress = new ServerAddress("59.110.236.9", 27017);  
    List<ServerAddress> addrs = new ArrayList<ServerAddress>();  
    addrs.add(serverAddress);  
      
    //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码  
    MongoCredential credential = MongoCredential.createScramSha1Credential("root", "admin", "example".toCharArray());  
    List<MongoCredential> credentials = new ArrayList<MongoCredential>();  
    credentials.add(credential);  
      
    //通过连接认证获取MongoDB连接  
    MongoClient mongoClient = new MongoClient(addrs,credentials);  
    return mongoClient;
  }

  @Override
  protected String getDatabaseName() {
    return "test";
  }
}

class Person {
  // 取得是默认_id字段，插入会生产_class字段
  private String id;
  private String name;
  private String company;
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getCompany() {
    return company;
  }
  public void setCompany(String company) {
    this.company = company;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  @Override
  public String toString() {
    return "Person [id=" + id + ", name=" + name + ", company=" + company + "]";
  }
  
  
}

interface PersonRepository extends CrudRepository<Person, Long> {

  List<Person> findByName(String name);
  
  List<Person> findByNameLike(String name);

}

@Service
class MyService {

  @Autowired
  private PersonRepository repository;

//  public MyService(PersonRepository repository) {
//    this.repository = repository;
//  }

  public void doWork() {

//     repository.deleteAll();

     Person person = new Person();
     person.setId("id123123123");
     person.setName("kanghongj");
     person.setCompany("ysn");
     person = repository.save(person);
//
     List<Person> lastNameResults = repository.findByName("kanghongj");
     if(lastNameResults!=null) {
       lastNameResults.forEach(p->System.out.println(p));
     }
     System.out.println("===========");
     List<Person> firstNameResults = repository.findByNameLike("z*");
     if(firstNameResults!=null) {
       firstNameResults.forEach(p->System.out.println(p));
     }
 }
  
}

