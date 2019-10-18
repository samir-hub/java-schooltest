package com.lambdaschool.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.service.CourseService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CourseController.class, secure = false)
public class CourseControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    private List<Course> courseList;

    @Before
    public void setUp() throws Exception
    {
        //MAKE A LIST FOR COURSES
        courseList = new ArrayList<>();

        //MAKE INSTRUCTORS
        Instructor i1 = new Instructor("Sally");
        Instructor i2 = new Instructor("Lucy");
        Instructor i3 = new Instructor("Charlie");

        //GIVE ID'S TO INSTRUCTORS
        i1.setInstructid(1);
        i2.setInstructid(2);
        i3.setInstructid(3);

        //MAKE A STUDENT LIST
        ArrayList<Student> students = new ArrayList<>();

        //MAKE THE STUDENTS
        Student s1 = new Student("John");
        Student s2 = new Student("Julian");
        Student s3 = new Student("Mary");

        //GIVE IDS TO STUDENTS
        s1.setStudid(1);
        s2.setStudid(2);
        s3.setStudid(3);

        //MAKE COURSES
        Course c1 = new Course("Data Science", i1);
        Course c2 = new Course("JavaScript", i1);
        Course c3 = new Course("Node.js", i1);
        Course c4 = new Course("Java Back End", i2);

        //GIVE IDS, INSTRUCTORS, AND STUDENTS TO COURSES
        c1.setCourseid(1);
        c1.setInstructor(i1);
        c1.setStudents(students);

        c2.setCourseid(2);
        c2.setInstructor(i2);
        c2.setStudents(students);

        c3.setCourseid(3);
        c3.setInstructor(i3);
        c3.setStudents(students);

        c4.setCourseid(4);
        c4.setInstructor(i2);
        c4.setStudents(students);

        //ADD COURSES TO COURSE LIST
        courseList.add(c1);
        courseList.add(c2);
        courseList.add(c3);
        courseList.add(c4);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void listAllCourses() throws Exception
    {
        String apiUrl = "/courses/courses";

        String courseName = "JavaScript";
        Instructor instructor = new Instructor("John");
        Course c1 = new Course(courseName,instructor);

        Mockito.when(courseService.findAll()).thenReturn((ArrayList<Course>) courseList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(courseList);

        assertEquals(er, tr);
    }

    @Test
    public void addNewCourse() throws Exception
    {
        String apiUrl = "/courses/course/add";

        // build a restaurant
//        ArrayList<RestaurantPayments> thisPay = new ArrayList<>();
        String rest3Name = "Course A";
        Instructor newInstructor = new Instructor("John");

        Course courseA = new Course(rest3Name, newInstructor);

        courseA.setCourseid(4);
        ObjectMapper mapper = new ObjectMapper();
        String courseString = mapper.writeValueAsString(courseA);

        Mockito.when(courseService.save(any(Course.class))).thenReturn(courseA);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(courseString);
        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }
}
