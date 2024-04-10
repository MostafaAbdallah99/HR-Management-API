package gov.iti.hr.soapcontrollers;


import gov.iti.hr.filters.EmployeeFilter;
import gov.iti.hr.models.EmployeeDTO;
import gov.iti.hr.models.EmployeeVacationDTO;
import gov.iti.hr.models.ManagerDTO;
import gov.iti.hr.persistence.entities.enums.Gender;
import gov.iti.hr.restcontrollers.beans.PaginationBean;
import gov.iti.hr.services.EmployeeService;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.ws.BindingType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
@BindingType(jakarta.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public class EmployeeWebService {
    private final EmployeeService employeeService;

    public EmployeeWebService() {
        employeeService = new EmployeeService();
    }

    public EmployeeDTO getEmployee(@WebParam(name = "EmployeeId") Integer id) {
        return employeeService.getEmployeeById(id);
    }

    public List<EmployeeDTO> getEmployees(@WebParam(name = "offset") Integer offset, @WebParam(name = "limit") Integer limit) {
        EmployeeFilter employeeFilter = new EmployeeFilter();
        PaginationBean paginationBean = new PaginationBean();
        paginationBean.setOffset(offset);
        paginationBean.setLimit(limit);
        employeeFilter.setPaginationBean(paginationBean);
        return employeeService.getAllEmployees(employeeFilter);
    }

    public EmployeeDTO addEmployee (
            @WebParam(name = "firstName") String firstName,
            @WebParam(name = "lastName") String lastName,
            @WebParam(name = "email") String email,
            @WebParam(name = "phoneNumber") String phoneNumber,
            @WebParam(name = "hireDate") String hireDate,
            @WebParam(name = "salary") BigDecimal salary,
            @WebParam(name = "vacationBalance") Integer vacationBalance,
            @WebParam(name = "managerEmail") String managerEmail,
            @WebParam(name = "jobName") String jobName,
            @WebParam(name = "departmentName") String departmentName,
            @WebParam(name = "Gender") Gender gender
            ) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName(firstName);
        employeeDTO.setLastName(lastName);
        employeeDTO.setEmail(email);
        employeeDTO.setPhoneNumber(phoneNumber);
        employeeDTO.setHireDate(LocalDate.parse(hireDate));
        employeeDTO.setSalary(salary);
        employeeDTO.setVacationBalance(vacationBalance);
        employeeDTO.setManagerEmail(managerEmail);
        employeeDTO.setJobName(jobName);
        employeeDTO.setDepartmentName(departmentName);
        employeeDTO.setGender(gender);
        return employeeService.saveEmployee(employeeDTO).getValue();
    }

    public String requestVacation(
            @WebParam(name = "EmployeeId") Integer id,
            @WebParam(name = "vacationStartDate") String vacationStartDate,
            @WebParam(name = "vacationEndDate") String vacationEndDate
    ) {
        EmployeeVacationDTO employeeVacationDTO = new EmployeeVacationDTO();
        employeeVacationDTO.setVacationStartDate(LocalDate.parse(vacationStartDate));
        employeeVacationDTO.setVacationEndDate(LocalDate.parse(vacationEndDate));
        employeeService.addVacation(id, employeeVacationDTO);
        return "Vacation requested successfully";
    }

    public ManagerDTO addManager (
            @WebParam(name = "firstName") String firstName,
            @WebParam(name = "lastName") String lastName,
            @WebParam(name = "email") String email,
            @WebParam(name = "phoneNumber") String phoneNumber,
            @WebParam(name = "hireDate") String hireDate,
            @WebParam(name = "salary") BigDecimal salary,
            @WebParam(name = "vacationBalance") Integer vacationBalance,
            @WebParam(name = "jobName") String jobName,
            @WebParam(name = "departmentName") String departmentName,
            @WebParam(name = "Gender") Gender gender
    ) {
        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setFirstName(firstName);
        managerDTO.setLastName(lastName);
        managerDTO.setEmail(email);
        managerDTO.setPhoneNumber(phoneNumber);
        managerDTO.setHireDate(LocalDate.parse(hireDate));
        managerDTO.setSalary(salary);
        managerDTO.setVacationBalance(vacationBalance);
        managerDTO.setJobName(jobName);
        managerDTO.setDepartmentName(departmentName);
        managerDTO.setGender(gender);
        return employeeService.saveManager(managerDTO).getValue();
    }

}
