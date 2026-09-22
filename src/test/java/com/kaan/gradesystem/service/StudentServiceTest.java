//@Mock = sahtesini oluştur.
//@InjectMocks = bu sahteleri test edeceğim gerçek sınıfın içine yerleştir.

package com.kaan.gradesystem.service;

import com.kaan.gradesystem.entity.Student;
import com.kaan.gradesystem.repository.StudentRepository;
import com.kaan.gradesystem.dto.StudentRequest;
import com.kaan.gradesystem.dto.StudentResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class) //JUnit'a “Bu testte Mockito kullanacağım” diyoruz
class StudentServiceTest {

    //Gerçek repository yerine sahte StudentRepository oluşturuyor.
    @Mock
    private StudentRepository studentRepository; 

    //Gerçek StudentService oluşturuyor ve yukarıdaki sahte studentRepository'yi bunun içine veriyor.
    //@InjectMocks = “Mock olarak oluşturduğum bağımlılıkları StudentService'in içine inject et.”
    @InjectMocks 
    private StudentService studentService;

    @Test
    void getAllStudents_ShouldReturnStudents(){

        Student student1 = new Student();
        student1.setName("Ertu");

        Student student2 = new Student();
        student2.setName("Kaan");

        List<Student> students = List.of(student1, student2);

        //“Birazdan studentRepository.findAll() çağrılırsa gerçek veritabanına gitme. Bana students listesini döndür.” ;
        // Repository'nin sahte cevabı
        when(studentRepository.findAll()).thenReturn(students);

        // Service artık Student değil StudentResponse döndürüyor.
        List<StudentResponse> result = studentService.getAllStudents(); 

        assertEquals(2, result.size()); //Çıkan sonuç beklediğimiz gibi mi?
        assertEquals("Ertu", result.get(0).getName());
        assertEquals("Kaan", result.get(1).getName());
        
        verify(studentRepository).findAll(); //“StudentService gerçekten studentRepository.findAll() metodunu çağırdı mı?”

    }

    @Test
    void saveStudent_ShouldReturnSavedStudent(){

        StudentRequest request = new StudentRequest();
        request.setName("Ali");

        Student student = new Student();
        student.setName("Ali");

        when(studentRepository.save(
                org.mockito.ArgumentMatchers.argThat(s ->
                        s.getName().equals("Ali")
                )
        )).thenReturn(student);

        StudentResponse result = studentService.saveStudent(request);

        assertEquals("Ali", result.getName());

        //assertEquals sonucu kontrol ediyor, verify ise Service ile Repository arasındaki etkileşimi kontrol ediyor.

        verify(studentRepository).save(
                org.mockito.ArgumentMatchers.argThat(s ->
                        s.getName().equals("Ali")
                )
        );
    }

    @Test
    void getStudentById_ShouldReturnStudent(){

        Student student = new Student();
        student.setName("Kaan");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        //Optional, bir değerin mevcut olabileceğini veya hiç bulunmayabileceğini güvenli şekilde temsil eden yapıdır
        //Optional.of(student) : “findById(1L) sonucunda bir öğrenci bulundu ve bulunan öğrenci bu.”

        StudentResponse result = studentService.getStudentById(1L);

        assertEquals("Kaan", result.getName());

        verify(studentRepository).findById(1L);
    }

    @Test
    void getStudentById_ShouldReturnNull_WhenStudentNotFound(){

        Long id = 99L;

        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        StudentResponse result = studentService.getStudentById(id);

        assertNull(result);

        verify(studentRepository).findById(id);
    }

    @Test
    void updateStudent_ShouldUpdateStudent() {

        Long id = 1L;

        Student existingStudent = new Student();
        existingStudent.setName("Ertuğrul Kaan");
        existingStudent.setEmail("eski@example.com");

        StudentRequest newStudent = new StudentRequest();
        newStudent.setName("Kaan");
        newStudent.setEmail("yeni@example.com");

        when(studentRepository.findById(id))
                .thenReturn(Optional.of(existingStudent));

        when(studentRepository.save(existingStudent))
                .thenReturn(existingStudent);

        StudentResponse result = studentService.updateStudent(id, newStudent);

        assertEquals("Kaan", result.getName());
        assertEquals("yeni@example.com", result.getEmail());

        verify(studentRepository).findById(id);
        verify(studentRepository).save(existingStudent);
    }


    @Test
    void deleteStudent_ShouldDeleteStudent(){

        Long id = 1L;

        studentService.deleteStudent(id);

        verify(studentRepository).deleteById(id);
    }
}