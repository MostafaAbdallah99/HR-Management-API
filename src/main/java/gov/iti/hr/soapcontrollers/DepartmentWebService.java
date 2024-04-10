package gov.iti.hr.soapcontrollers;

import gov.iti.hr.filters.DepartmentFilter;
import gov.iti.hr.models.DepartmentDTO;
import gov.iti.hr.restcontrollers.beans.PaginationBean;
import gov.iti.hr.services.DepartmentService;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public class DepartmentWebService {
    private final DepartmentService departmentService;

    public DepartmentWebService() {
        departmentService = new DepartmentService();
    }

    public List<DepartmentDTO> getDepartments(@WebParam(name = "Offset") Integer offset, @WebParam(name = "Limit") Integer limit) {
        DepartmentFilter departmentFilter = new DepartmentFilter();
        PaginationBean paginationBean = new PaginationBean();
        paginationBean.setLimit(limit);
        paginationBean.setOffset(offset);
        departmentFilter.setPaginationBean(paginationBean);
        return departmentService.findAllDepartments(departmentFilter);
    }

    public DepartmentDTO getDepartmentById(@WebParam(name = "DepartmentId") Integer departmentId) {
        return departmentService.getDepartmentById(departmentId);
    }

    public String saveDepartment(@WebParam(name = "DepartmentName") String departmentName) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDepartmentName(departmentName);
        return "Department saved successfully with id: " + departmentService.saveDepartment(departmentDTO);
    }

    public String saveDepartmentWithManager(@WebParam(name = "DepartmentName") String departmentName, @WebParam(name = "ManagerId") Integer managerId) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDepartmentName(departmentName);
        departmentDTO.setManagerId(managerId);
        return "Department saved successfully with id: " + departmentService.saveDepartment(departmentDTO);
    }

    public String updateDepartment(@WebParam(name = "DepartmentId") Integer departmentId, @WebParam(name = "DepartmentName") String departmentName, @WebParam(name = "ManagerId") Integer managerId) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDepartmentId(departmentId);
        departmentDTO.setDepartmentName(departmentName);
        departmentDTO.setManagerId(managerId);
        departmentService.updateDepartment(departmentDTO);
        return "Department updated successfully";
    }

    public String deleteDepartment(@WebParam(name = "DepartmentId") Integer departmentId) {
        departmentService.deleteDepartment(departmentId);
        return "Department deleted successfully";
    }
}
