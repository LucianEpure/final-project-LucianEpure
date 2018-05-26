import dto.RequestDto;
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
import service.request.RequestService;
import service.type.TypeService;

import static application.Constants.INFANTRY;

@RunWith(SpringRunner.class)
@Configuration
@EnableJpaRepositories(basePackages = {"repository"})
@ComponentScan({"entity","application","repository","converter","config","controller","dto","service","validators"})
@EntityScan(basePackages ={"entity"})
public class RequestServiceTest {

    private static final String LOCATION = "Maldive";
    private static final String LOCATIONUPDATED = "Panama";

    @Autowired
    RequestService requestService;

    @Autowired
    TypeService typeService;

    @Before
    public void setup() throws Exception {
        requestService.removeAll();
    }
    @Test
    public void addRequest(){
        RequestDto requestDto = new RequestDto();
        requestDto.setLocationName(LOCATION);
        requestService.addRequest(requestDto);
        Assert.assertTrue(requestService.showAll().size()==1);
    }
    @Test
    public void addUnit(){
        RequestDto requestDto = new RequestDto();
        requestDto.setLocationName(LOCATION);
        typeService.addNewType(INFANTRY);
        requestService.addUnit(requestDto,INFANTRY);
        requestService.addRequest(requestDto);
        Assert.assertTrue(requestService.showAll().get(0).getTypes().get(0).getTypeName().equals(INFANTRY));
    }
    @Test
    public void update(){
        RequestDto requestDto = new RequestDto();
        requestDto.setLocationName(LOCATION);
        typeService.addNewType(INFANTRY);
        requestService.addUnit(requestDto,INFANTRY);
        requestService.addRequest(requestDto);
        RequestDto requestDto1 = requestService.findByLocation(LOCATION);
        requestDto1.setLocationName(LOCATIONUPDATED);
        requestService.updateRequest(requestDto1);
        Assert.assertNotNull(requestService.findByLocation(LOCATIONUPDATED));
    }

}
