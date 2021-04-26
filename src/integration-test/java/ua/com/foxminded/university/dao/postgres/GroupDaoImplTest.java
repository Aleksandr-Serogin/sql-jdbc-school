package ua.com.foxminded.university.dao.postgres;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.university.dao.DaoFactory;
import ua.com.foxminded.university.dao.postgres.util.CreateDeleteDate;
import ua.com.foxminded.university.domain.entity.Group;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupDaoImplTest {
    private final DaoFactory daoFactory = new DaoFactory("config.properties");
    private final GroupDaoImpl groupDao = new GroupDaoImpl(daoFactory);
    private final CreateDeleteDate createDeleteDate = new CreateDeleteDate();

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        createDeleteDate.createDate();
    }

    @AfterEach
    void tearDown() {
        createDeleteDate.deleteDate();
    }

    @Test
    void getGroupsWithLess_EqualsStudent_ShouldReturnListGroups_WhenCorrectData() {
        List<Group> groups = groupDao.findAll();
        for (Group group : groups) {
            int groupId = group.getGroupId();
            String groupName = group.getGroupName();
            assertTrue(Integer.toString(groupId).matches("^\\d+$"));
            assertTrue(groupName.matches("^[a-z]+-+[0-9]{2}$"));
        }
    }

    @Test
    void create_shouldReturnCorrectIdGroup_whenCorrectDataInserted() {
        Group testGroup = new Group();
        testGroup.setGroupName("as-03");
        groupDao.create(testGroup);
        assertEquals(11, testGroup.getGroupId());
    }

    @Test
    void findById_shouldReturnCorrectGroup_whenCorrectDataInserted() {
        Group testGroup = new Group();
        testGroup.setGroupId(11);
        testGroup.setGroupName("as-03");
        groupDao.create(testGroup);
        List<Group> groups = groupDao.findById(11);
        for (Group group : groups) {
            assertEquals(testGroup, group);
        }
    }

    @Test
    public void findAll_shouldReturnNotEmptyListOfGroups() {
        List<Group> groups = groupDao.findAll();
        for (Group group : groups) {
            int groupId = group.getGroupId();
            String groupName = group.getGroupName();
            assertTrue(Integer.toString(groupId).matches("^\\d+$"));
            assertTrue(groupName.matches("^[-a-z]{2}-[-0-9]{2}"));
        }
    }

    @Test
    void update_shouldUpdateGroup_whenCorrectDataInserted() {
        Group testGroup = new Group();
        testGroup.setGroupId(10);
        testGroup.setGroupName("as-03");
        groupDao.update(testGroup);
        List<Group> groups = groupDao.findById(10);
        for (Group group : groups) {
            assertEquals(testGroup, group);
        }
    }

    @Test
    void findById_shouldThrowRuntimeException_whenFindInputIdIsDelete() {
        groupDao.delete(10);
        assertThrows(RuntimeException.class,
                () -> groupDao.findById(10));
    }

    @Test
    void getGroupsWithLess_EqualsStudent_ShouldReturnListGroups_whenCorrectDataInserted(){
        List<Group> groups =  groupDao.getGroupsWithLess_EqualsStudent(20);
        for (Group group : groups) {
            int groupId = group.getGroupId();
            String groupName = group.getGroupName();
            assertTrue(Integer.toString(groupId).matches("^\\d+$"));
            assertTrue(groupName.matches("^[-a-z]{2}-[-0-9]{2}"));
        }
    }

    @Test
    void getGroupsWithLess_EqualsStudent_shouldThrowRuntimeException_whenNotFind() {
        assertThrows(RuntimeException.class,
                () -> groupDao.getGroupsWithLess_EqualsStudent(2));
    }

    @Test
    void delete_shouldReturnZero_whenInputIdIsInvalid() {
        assertEquals(0, groupDao.delete(12));
    }

    @Test
    void delete_shouldReturnOne_whenInputIdIsDelete() {
        assertEquals(1, groupDao.delete(10));
    }
}