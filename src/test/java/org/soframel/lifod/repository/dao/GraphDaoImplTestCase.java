package org.soframel.lifod.repository.dao;

import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.soframel.lifod.Application;
import org.soframel.lifod.config.DatabaseConfiguration;
import org.soframel.lifod.domain.EndOfLife;
import org.soframel.lifod.domain.User;
import org.soframel.lifod.repository.EndOfLifeRepository;
import org.soframel.lifod.repository.UserRepository;
import org.soframel.lifod.repository.dao.GraphData;
import org.soframel.lifod.repository.dao.GraphsDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GraphDaoImplTestCase {

	@Autowired
	private DatabaseConfiguration databaseConfiguration;
	
	@Autowired
	private EndOfLifeRepository endOfLifeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void test() throws Exception{
		User admin = userRepository.findOneByLogin("admin").get();
		GraphsDaoImpl dao=databaseConfiguration.graphDao();

		EndOfLife eof=new EndOfLife();
		eof.setBrand("TESTBrand");
		LocalDate date1=LocalDate.of(2009, 12, 21);		
		eof.setBuyingDate(date1);
		LocalDate date2=LocalDate.of(2012, 01, 01);		
		eof.setEndOfLifeDate(date2);
		eof.setProduct("Phone4G");
		eof.setProductVersion("1.1");
		eof.setReason("failure");
		eof.setUser(admin.getId());
		endOfLifeRepository.save(eof);
		
		EndOfLife eof2=new EndOfLife();
		eof2.setBrand("TESTBrand");
		LocalDate date1b=LocalDate.of(2005, 12, 21);		
		eof2.setBuyingDate(date1b);
		LocalDate date2b=LocalDate.of(2012, 01, 01);		
		eof2.setEndOfLifeDate(date2b);
		eof2.setProduct("TV");
		eof2.setProductVersion("0");
		eof2.setReason("failure");
		eof2.setUser(admin.getId());
		endOfLifeRepository.save(eof2);
		
		EndOfLife eof3=new EndOfLife();
		eof3.setBrand("Another brand");
		LocalDate date1c=LocalDate.of(2001, 12, 21);		
		eof3.setBuyingDate(date1c);
		LocalDate date2c=LocalDate.of(2015, 01, 01);		
		eof3.setEndOfLifeDate(date2c);
		eof3.setProduct("Fridge");
		eof3.setProductVersion("0");
		eof3.setReason("KO");
		eof3.setUser(admin.getId());
		endOfLifeRepository.save(eof3);
		
		List<GraphData> result=dao.getLifespanByBrand("TESTBrand");
		Assert.assertNotNull(result);
		Assert.assertTrue(result.size()==2);
		for(GraphData data: result){
			System.out.println("data: "+data);
			if(data.getName().equals("TESTBrand")){
				Assert.assertEquals(127137600000l, data.getAverage());
			}
			else if(data.getName().equals("Another brand")){
				Assert.assertEquals(411177600000l, data.getAverage());
			}
			else
				Assert.fail("Brand group not recognized: "+data.getName());
		}
	}
}
