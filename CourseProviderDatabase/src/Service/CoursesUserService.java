package Service;

import Exceptions.EntityNotFoundException;
import Models.*;
import Repository.IRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CoursesUserService {
    private final IRepository<Course> courseIRepository;
    private final IRepository<Student> studentIRepository;
    private final IRepository<Instructor> instructorIRepository;
    private final IRepository<Admin> adminIRepository;
    private final IRepository<Enrolled> enrolledIRepository;


    /**
     * Constructs a new {@code CoursesUserService} with the specified repositories.
     * This service manages the interactions between courses, students, instructors, and admins.
     * The constructor allows dependency injection for the repositories that handle course, student, instructor, and admin data.
     *
     * @param courseIRepository     The repository interface for course data, used to fetch and manage course-related information.
     * @param studentIRepository    The repository interface for student data, used to fetch and manage student-related information.
     * @param instructorIRepository The repository interface for instructor data, used to fetch and manage instructor-related information.
     * @param adminIRepository      The repository interface for admin data, used to fetch and manage admin-related information.
     */
    public CoursesUserService(IRepository<Course> courseIRepository, IRepository<Student> studentIRepository, IRepository<Instructor> instructorIRepository, IRepository<Admin> adminIRepository, IRepository<Enrolled> enrolledIRepository) {
        this.courseIRepository = courseIRepository;
        this.studentIRepository = studentIRepository;
        this.instructorIRepository = instructorIRepository;
        this.adminIRepository = adminIRepository;
        this.enrolledIRepository = enrolledIRepository;
    }

    /**
     * Retrieves a list of all students enrolled in a specific course.
     *
     * @param courseId The ID of the course.
     * @return A list of students enrolled in the specified course.
     */
    public List<Student> getEnrolledStudents(Integer courseId) throws EntityNotFoundException {
        Course course = courseIRepository.get(courseId);
        if (course == null)
            throw new EntityNotFoundException(courseId);


        List<Enrolled> enrollments = enrolledIRepository.getAll();
        List<Integer> enrolledStudentIds = enrollments.stream()
                .filter(e -> e.getCourseId().equals(courseId))
                .map(Enrolled::getId)
                .collect(Collectors.toList());


        //Fetch student objects for each student
        List<Student> enrolledStudents = new ArrayList<>();
        for(Integer studentId : enrolledStudentIds){
            Student student = studentIRepository.get(studentId);
            if (student != null)
                enrolledStudents.add(student);
        }

        return enrolledStudents;


    }

    /**
     * Retrieves the instructor that teaches a specific course.
     *
     * @param courseId The ID of the course.
     * @return A list of students enrolled in the specified course.
     */
    public Instructor getAssignedInstructor(Integer courseId)throws EntityNotFoundException {
        Course course = courseIRepository.get(courseId);
        Instructor instructor;
        if(course == null)
            throw new EntityNotFoundException(courseId);
        instructor = instructorIRepository.get(course.getInstructorId());
        return instructor;
    }

    /**
     * Enrolls a student in a course if there are available spots.
     *
     * @param studId   The ID of the student to enroll.
     * @param courseId The ID of the course.
     */
    public void enroll(Integer studId, Integer courseId)throws EntityNotFoundException {
        Student student = studentIRepository.get(studId);
        if(student == null)
            throw new EntityNotFoundException(studId);

        Course course = courseIRepository.get(courseId);
        if(course == null)
            throw new EntityNotFoundException(courseId);

        if (course.getAvailableSpots() <= getEnrolledStudents(courseId).size())
            throw new IllegalArgumentException("Course is already at full capacity");

        //check if the student is already enrolled
        List<Enrolled> enrollments = enrolledIRepository.getAll();
        boolean alreadyEnrolled = enrollments.stream().anyMatch(e -> e.getId().equals(studId) && e.getCourseId().equals(courseId));

        if (alreadyEnrolled)
            throw new IllegalArgumentException("Student is already enrolled");

        //create new enrollment
        Enrolled newEnrollment = new Enrolled(studId,courseId);
        enrolledIRepository.create(newEnrollment);

        //update course availability
        course.setAvailableSpots(course.getAvailableSpots() - 1);
        courseIRepository.update(course);

        //update the Students list of courses
        List<Integer> studentCourses = student.getCourses();
        studentCourses.add(courseId);
        student.setCourses(studentCourses);

        //update the Courses list of students
        List<Integer> coursesStudents = course.getEnrolledStudents();
        coursesStudents.add(studId);
        course.setEnrolledStudents(coursesStudents);


    }

    /**
     * Assigns a instructor to teach a course, the current instructor of the course will be overwritten.
     *
     * @param instructorId The ID of the student to enroll.
     * @param courseId     The ID of the course.
     */
    public void assignInstructor(Integer instructorId, Integer courseId)throws EntityNotFoundException {
        // Fetch the instructor from the repository
        Instructor instructor = instructorIRepository.get(instructorId);
        if (instructor == null)
            throw new EntityNotFoundException(instructorId);

        // Fetch the course from the repository
        Course course = courseIRepository.get(courseId);
        if (course == null)
            throw new EntityNotFoundException(courseId);

        // Check if the instructor is already assigned to the course
        if (course.getInstructorId() != null && course.getInstructorId().equals(instructorId)) {
            throw new IllegalArgumentException("Instructor with id " + instructorId + " is already teaching this course");
        }

        //Add the course to the list of courses an instructor teaches
        List<Integer> assignedCourses = instructor.getCourses();
        assignedCourses.add(courseId);
        instructor.setCourses(assignedCourses);

        // Assign the instructor to the course (overwriting any existing assignment)
        course.setInstructorId(instructorId);

        // Update the instructor and course in the repository
        instructorIRepository.update(instructor);
        courseIRepository.update(course);

        System.out.println("Instructor with id " + instructorId + " has been assigned to course with id " + courseId);
    }

    public void unAssignInstructor(Integer instructorId, Integer courseId)throws EntityNotFoundException {
        // Fetch the instructor from the repository
        Instructor instructor = instructorIRepository.get(instructorId);
        if (instructor == null)
            throw new EntityNotFoundException(instructorId);

        // Fetch the course from the repository
        Course course = courseIRepository.get(courseId);
        if (course == null)
            throw new EntityNotFoundException(courseId);

        // Check if the instructor is assigned to the course
        if (course.getInstructorId() == null && !course.getInstructorId().equals(instructorId)) {
            throw new IllegalArgumentException("Instructor with id " + instructorId + " is not teaching this course");
        }

        //Remove the course from the list of courses an instructor teaches
        List<Integer> assignedCourses = instructor.getCourses();
        assignedCourses.remove(courseId);
        instructor.setCourses(assignedCourses);

        // Unassign the instructor from the course (overwriting any existing assignment)
        course.setInstructorId(null);

        // Update the instructor and course in the repository
        instructorIRepository.update(instructor);
        courseIRepository.update(course);

        System.out.println("Instructor with id " + instructorId + " has been unassigned from course with id " + courseId);
    }

    /**
     * Adds a new course to the repository.
     *
     * @param course The course to add.
     */
    public void addCourse(Course course) {
        courseIRepository.create(course);
    }

    /**
     * Adds a new student to the repository.
     *
     * @param student The student to add.
     */
    public void addStudent(Student student) {
        studentIRepository.create(student);
    }

    /**
     * Adds a new instructor to the repository.
     *
     * @param instructor The instructor to add.
     */
    public void addInstructor(Instructor instructor) {
        instructorIRepository.create(instructor);
    }

    /**
     * Adds a new admin to the repository.
     *
     * @param admin The instructor to add.
     */
    public void addAdmin(Admin admin) {
        adminIRepository.create(admin);
    }

    /**
     * Removes a course from the system.
     *
     * Before removing the course, the following steps are performed:
     * - Unassign the instructor from the course (if any).
     * - Unenroll all students from the course.
     *
     * @param courseId The ID of the course to be removed.
     * @throws IllegalArgumentException if the course with the given ID does not exist.
     */
    public void removeCourse(Integer courseId) throws EntityNotFoundException {
        // Check if course exists
        Course course = courseIRepository.get(courseId);
        if (course == null) {
            throw new EntityNotFoundException(courseId);
        }

        // Before deleting, ensure that there are no students or instructors assigned to this course.
        // Example: You may choose to unenroll students or unassign the instructor.

        // Unassign the instructor from the course (if any)
        if (course.getInstructorId() != null) {
            Instructor instructor = instructorIRepository.get(course.getInstructorId());
            if (instructor != null) {
                // Unassign instructor from course
                List<Integer> assignedCourses = instructor.getCourses();
                assignedCourses.remove(courseId);
                instructor.setCourses(assignedCourses);
                instructorIRepository.update(instructor);
            }
        }

        // Unenroll students from the course
        List<Enrolled> enrollments = enrolledIRepository.getAll();
        for (Enrolled enrollment : enrollments) {
            if (enrollment.getCourseId().equals(courseId)) {
                // Unenroll the student
                Student student = studentIRepository.get(enrollment.getId());
                if (student != null) {
                    // Remove the course from the student's list of enrolled courses
                    List<Integer> studentCourses = student.getCourses();
                    studentCourses.remove(courseId);
                    student.setCourses(studentCourses);
                    studentIRepository.update(student);

                    // Remove the student from the course's enrolled students
                    List<Integer> courseStudents = course.getEnrolledStudents();
                    courseStudents.remove(enrollment.getId());
                    course.setEnrolledStudents(courseStudents);
                }

                // Delete the enrollment record
                enrolledIRepository.delete(enrollment.getId());
            }
        }

        // Now delete the course itself
        courseIRepository.delete(courseId);
        System.out.println("Course with id " + courseId + " has been successfully removed");
    }

    /**
     * Removes an instructor from the system.
     *
     * Before removing the instructor, the following steps are performed:
     * - Unassign the instructor from any courses they are teaching.
     * - Remove the instructor from any courses that they are teaching.
     *
     * @param instructorId The ID of the instructor to be removed.
     * @throws IllegalArgumentException if the instructor with the given ID does not exist.
     */
    public void removeInstructor(Integer instructorId) throws EntityNotFoundException {
        // Retrieve the instructor to ensure they exist
        Instructor instructor = instructorIRepository.get(instructorId);
        if (instructor == null) {
            throw new EntityNotFoundException(instructorId);
        }


        List<Course> courses = courseIRepository.getAll();
        for(Course c : courses)
            if(c.getInstructorId() == instructorId){
                c.setInstructorId(null);
                courseIRepository.update(c);

            }

        // Now, remove the instructor from the system
        instructorIRepository.delete(instructorId);
        System.out.println("Instructor with id " + instructorId + " has been successfully removed");
    }

    /**
     * Removes a student from the system.
     *
     * Before removing the student, the following steps are performed:
     * - Unenroll the student from all courses they are enrolled in.
     * - Remove the student from any course's list of enrolled students.
     *
     * @param studentId The ID of the student to be removed.
     * @throws IllegalArgumentException if the student with the given ID does not exist.
     */
    public void removeStudent(Integer studentId) throws EntityNotFoundException {
        // Retrieve the student to ensure they exist
        Student student = studentIRepository.get(studentId);
        if (student == null) {
            throw new EntityNotFoundException(studentId);
        }

        // Retrieve all enrollments for the student
        List<Enrolled> enrollments = enrolledIRepository.getAll();
        for (Enrolled enrollment : enrollments) {
            if (enrollment.getId().equals(studentId)) {
                // Retrieve the course the student is enrolled in
                Course course = courseIRepository.get(enrollment.getCourseId());

                if (course != null) {
                    // Remove the student from the course's list of enrolled students
                    List<Integer> courseStudents = course.getEnrolledStudents();
                    courseStudents.remove(studentId);
                    course.setEnrolledStudents(courseStudents);
                    courseIRepository.update(course);
                }

                // Remove the student from the student's list of enrolled courses
                List<Integer> studentCourses = student.getCourses();
                studentCourses.remove(enrollment.getCourseId());
                student.setCourses(studentCourses);
                studentIRepository.update(student);

                // Delete the enrollment record
                enrolledIRepository.delete(enrollment.getId());
            }
        }

        // Now, delete the student
        studentIRepository.delete(studentId);
        System.out.println("Student with id " + studentId + " has been successfully removed");
    }

    /**
     * Removes a admin from the repository.
     *
     * @param adminId The id of the admin to remove.
     */
    public void removeAdmin(Integer adminId) throws EntityNotFoundException {
        Admin admin = adminIRepository.get(adminId);
        if (admin == null) {
            throw new EntityNotFoundException(adminId);
        }
        adminIRepository.delete(adminId);
        System.out.println("Admin with id " + adminId + " has been successfully removed");
    }


    public List<Course> getAllCourses(){
        return courseIRepository.getAll();
    }

    public  List<Student> getAllStudents(){
        return studentIRepository.getAll();
    }


    public List<Instructor> getAllInstructors(){
        return instructorIRepository.getAll();
    }

    public List<Admin> getAllAdmins(){
        return adminIRepository.getAll();
    }


    public void unenroll(Integer studId, Integer courseId) throws EntityNotFoundException {

        Student student = studentIRepository.get(studId);
        if (student == null)
            throw new EntityNotFoundException(studId);

        Course course = courseIRepository.get(courseId);
        if (course == null)
            throw new EntityNotFoundException(courseId);

        // Check if the student is enrolled in the course
        List<Enrolled> enrollments = enrolledIRepository.getAll();
        boolean isEnrolled = enrollments.stream()
                .anyMatch(e -> e.getId().equals(studId) && e.getCourseId().equals(courseId));

        if (!isEnrolled)
            throw new IllegalArgumentException("Student is not enrolled in this course");

        List<Enrolled>enrollmentsThatDonMatch  = new ArrayList<>();
        for(Enrolled enrollment : enrollments)
            if (enrollment.getId() == studId && enrollment.getCourseId() != courseId)
                enrollmentsThatDonMatch.add(enrollment);


        // Find and remove the specific enrollment
        for (Enrolled enrollment : enrollments) {
            if (enrollment.getId().equals(studId) && enrollment.getCourseId().equals(courseId)) {
                enrolledIRepository.delete(enrollment.getId());
                break; // Exit the loop after removing the target enrollment
            }
        }

        for(Enrolled enrollment : enrollmentsThatDonMatch)
            enrolledIRepository.create(enrollment);

        // Update course availability
        course.setAvailableSpots(course.getAvailableSpots() + 1);

        // Update the student's list of enrolled courses (use a copy)
        List<Integer> studentCourses = new ArrayList<>(student.getCourses());
        studentCourses.remove(courseId);
        student.setCourses(studentCourses); // Set the modified list back to the student

        // Update the course's list of enrolled students (use a copy)
        List<Integer> courseStudents = new ArrayList<>(course.getEnrolledStudents());
        courseStudents.remove(studId);
        course.setEnrolledStudents(courseStudents); // Set the modified list back to the course

        // Save the updated student and course objects
        studentIRepository.update(student);
        courseIRepository.update(course);
    }



    public void removeAssignedInstructor(Integer instructorId, Integer courseId) throws EntityNotFoundException {
        //fetch the instructor
        Instructor instructor = instructorIRepository.get(instructorId);
        if (instructor == null)
            throw new EntityNotFoundException(instructorId);

        // Fetch the course from the repository
        Course course = courseIRepository.get(courseId);
        if (course == null)
            throw new EntityNotFoundException(courseId);

        //check if the instructor is assigned to a course
        if(course.getInstructorId() == null)
            throw new IllegalArgumentException("Course already been unassigned");

        //remove the course from the instructor list
        List<Integer> assignedCourses = instructor.getCourses();
        assignedCourses.remove(courseId);
        instructor.setCourses(assignedCourses);

        //unassign the instructor from the course
        course.setInstructorId(null);

        //update the instructor and course
        instructorIRepository.update(instructor);
        courseIRepository.update(course);

        System.out.println("Instructor with id " + instructorId + " has been unassigned from thr course with id " + courseId);
    }


    public List<Course> getCoursesAStudentEnrolledIn(Integer studentId) throws EntityNotFoundException {
        Student student = studentIRepository.get(studentId);
        if(student == null)
            throw new EntityNotFoundException(studentId);

        List<Enrolled> allEnrollments = enrolledIRepository.getAll();

        List<Integer> studentCourseIds = allEnrollments.stream()
                .filter(e -> e.getId().equals(studentId))
                .map(Enrolled::getCourseId)
                .collect(Collectors.toList());

        return studentCourseIds.stream()
                .map(courseIRepository::get)
                .collect(Collectors.toList());



    }



    public List<Course> getCoursesAInstructorTeaches(Integer instructorId){
        List<Enrolled> enrollments = enrolledIRepository.getAll();

        //extract course ids
        List<Integer> enrolledCourseIds = enrollments.stream().map(Enrolled::getCourseId).distinct().collect(Collectors.toList());

        //fetch the course object for each course
        List<Course> enrolledCourses = new ArrayList<>();
        for(Integer courseId : enrolledCourseIds){
            Course course = courseIRepository.get(courseId);
            if(course != null && course.getInstructorId().equals(instructorId))
                enrolledCourses.add(course);
        }

        return enrolledCourses;
    }

    public Course getCourseInfo(Integer courseId)throws EntityNotFoundException{
        Course course = courseIRepository.get(courseId);
        if (course == null)
            throw new EntityNotFoundException(courseId);
        return course;
    }

    public Student getStudentInfo(Integer studentId)throws EntityNotFoundException{
        Student student = studentIRepository.get(studentId);
        if (student == null)
            throw new EntityNotFoundException(studentId);
        return student;
    }

    public Instructor getInstructorInfo(Integer instructorId)throws EntityNotFoundException{
        Instructor instructor = instructorIRepository.get(instructorId);
        if (instructor == null)
            throw new EntityNotFoundException(instructorId);
        return instructor;
    }

    public void updateCourse(Course course)throws EntityNotFoundException{

        if(courseIRepository.get(course.getId()) == null)
            throw new EntityNotFoundException(course.getId());
        courseIRepository.update(course);
    }

    public void updateStudent(Student student)throws EntityNotFoundException{
        if (studentIRepository.get(student.getId()) == null)
            throw new EntityNotFoundException(student.getId());
        studentIRepository.update(student);
    }

    public void updateInstructor(Instructor instructor)throws EntityNotFoundException{
        if (instructorIRepository.get(instructor.getId()) == null)
            throw new EntityNotFoundException(instructor.getId());
        instructorIRepository.update(instructor);
    }

    public List<Course> getAllUnderOcupiedCourses(){
        List<Course>courses = courseIRepository.getAll();
        List<Course> underOcupiedCourses = new ArrayList<>();
        for(Course course : courses){
            if (course.getEnrolledStudents().size() <= course.getAvailableSpots() * 0.2){
                underOcupiedCourses.add(course);
            }
        }
        return underOcupiedCourses;
    }


    //sorting in ascending order
    public List<Instructor> sortAllInstructorsByNumberOfTeachingCourses(){
        List<Instructor> instructors = instructorIRepository.getAll();
        //sort the instructor by number of courses
        instructors.sort((instructor1, instructor2) -> java.lang.Integer.compare(
                instructor2.getCourses().size(), instructor1.getCourses().size()
        ));
        return instructors;
    }


    public List<Course> getAllCoursesThatEndBeforeADate(String date){
        List<Course>courses = courseIRepository.getAll();
        List<Course>coursesThatEndBeforeADate = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date inputDate = dateFormat.parse(date);

            for(Course course : courses){
                try {
                    Date courseEndDate = dateFormat.parse(course.getEndDate());

                    if(courseEndDate.before(inputDate)){
                        coursesThatEndBeforeADate.add(course);
                    }
                }catch(ParseException e){
                    System.err.println("Invalid end date format for course: " + course.getCourseTitle());
                }
            }
        } catch (ParseException e) {
            System.err.println("Invalid input date format. Please use the format yyyy-MM-dd.");
        }
        return coursesThatEndBeforeADate;
    }

    /**
     * Retrieves a list of instructors sorted by the total number of students enrolled in the courses they teach.
     *
     * @return A list of instructors sorted by total enrollment in descending order.
     */
    public List<Instructor> getInstructorsSortedByEnrollment() {
        List<Instructor> allInstructors = instructorIRepository.getAll();
        List<Course> allCourses = courseIRepository.getAll();
        List<Enrolled> allEnrollments = enrolledIRepository.getAll();

        // Create a map of course ID to number of enrolled students
        Map<Integer, Long> courseEnrollmentCounts = allEnrollments.stream()
                .collect(Collectors.groupingBy(Enrolled::getCourseId, Collectors.counting()));

        // Calculate total enrollment for each instructor
        Map<Integer, Long> instructorEnrollmentCounts = allCourses.stream()
                .collect(Collectors.groupingBy(
                        Course::getInstructorId,
                        Collectors.summingLong(course -> courseEnrollmentCounts.getOrDefault(course.getId(), 0L))
                ));

        // Sort instructors by total enrollment
        return allInstructors.stream()
                .sorted(Comparator.comparingLong((Instructor i) ->
                        instructorEnrollmentCounts.getOrDefault(i.getId(), 0L)).reversed())
                .collect(Collectors.toList());
    }








}
