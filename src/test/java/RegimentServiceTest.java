import dto.RegimentDto;
import dto.RequestDto;
import dto.SupplyDto;
import entity.Type;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import service.regiment.RegimentService;
import service.regiment.UserService;
import service.request.RequestService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static application.Constants.RECRUITS;

@RunWith(SpringRunner.class)
@Configuration
@EnableJpaRepositories(basePackages = {"repository"})
@ComponentScan({"entity","application","repository","converter","config","controller","dto","service","validators"})
@EntityScan(basePackages ={"entity"})

public class RegimentServiceTest {

    @Autowired
    RegimentService regimentService;

    @Autowired
    RequestService requestService;

    @Autowired
    UserService userService;

    private static final String PASSWORD = "aB123456!";
    private static final String LOCATION = "Maldive";
    private static final int UPDATEDFOOD = 25;
    private static final int CODE = 123456;

    @Before
    public void setup() throws Exception {
        regimentService.removeAll();
    }

    @Test
    public void addRegiment(){

        regimentService.enlistRegiment(CODE,PASSWORD);
        Assert.assertTrue(regimentService.showAll().size()==1);
    }

    @Test
    public void sendToWar(){
        regimentService.enlistRegiment(CODE,PASSWORD);
        RequestDto requestDto = new RequestDto();
        requestDto.setLocationName(LOCATION);
        requestDto = requestService.addUnit(requestDto,RECRUITS);
        requestService.addRequest(requestDto);
        regimentService.sendRegimentToWar(CODE,requestDto);
        Assert.assertTrue(regimentService.showAll().size()==0);
    }

    @Test
    public void update(){
        regimentService.enlistRegiment(CODE,PASSWORD);
        RegimentDto regimentDto = regimentService.findByCode(CODE);
        SupplyDto supplyDto = new SupplyDto();
        supplyDto.setFood(UPDATEDFOOD);
        regimentDto.setStamina(15);
        regimentService.update(regimentDto,supplyDto);
        Assert.assertTrue(regimentService.findByCode(CODE).getStamina()==15);
    }

    @Test
    public void findRegFromUser(){
        regimentService.enlistRegiment(CODE,PASSWORD);
        String username = userService.findUser(CODE).getUsername();
        Assert.assertTrue(regimentService.extractRegimentFromUser(username)==CODE);
    }
}
