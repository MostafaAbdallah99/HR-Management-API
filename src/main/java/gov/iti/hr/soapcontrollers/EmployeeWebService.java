package gov.iti.hr.soapcontrollers;


import gov.iti.hr.filters.EmployeeFilter;
import gov.iti.hr.models.EmployeeDTO;
import gov.iti.hr.restcontrollers.beans.PaginationBean;
import gov.iti.hr.services.EmployeeService;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.ws.BindingType;

import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
@BindingType(jakarta.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public class EmployeeWebService {
    private final EmployeeService employeeService;

    public EmployeeWebService() {
        employeeService = new EmployeeService();
    }

    public String getEmployee(@WebParam(name = "EmployeeId") Integer id) {
        return employeeService.getEmployeeById(id).toString();
    }

    public List<EmployeeDTO> getEmployees(@WebParam(name = "offset") Integer offset, @WebParam(name = "limit") Integer limit) {
        EmployeeFilter employeeFilter = new EmployeeFilter();
        PaginationBean paginationBean = new PaginationBean();
        paginationBean.setOffset(offset);
        paginationBean.setLimit(limit);
        employeeFilter.setPaginationBean(paginationBean);
        return employeeService.getAllEmployees(employeeFilter);
    }

}
