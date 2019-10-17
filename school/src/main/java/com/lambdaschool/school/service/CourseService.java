package com.lambdaschool.school.service;

import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.view.CountStudentsInCourses;

import java.util.ArrayList;
import java.util.Optional;

public interface CourseService
{
    ArrayList<Course> findAll();

    Optional<Course> findCourseById(long id);

    ArrayList<CountStudentsInCourses> getCountStudentsInCourse();

    Course save (Course course);

    void delete(long id);
}
