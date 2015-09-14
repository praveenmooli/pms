package com.arrkgroup.apps.hr.assignobjective;

import java.util.List;

import com.arrkgroup.apps.form.RoleObjectivesBean;
import com.arrkgroup.apps.model.ObjectivesModel;
import com.arrkgroup.apps.model.Role;
import com.arrkgroup.apps.model.SectionModel;

public interface RoleService {

	public List<Role> showRole();

	public boolean insert_record(RoleObjectivesBean bean);

	public boolean delete_record(RoleObjectivesBean bean);


	public boolean checkRoleObjectives(RoleObjectivesBean bean);

	public List<ObjectivesModel> showObjectives_By_Section(int section_id);

	public List<ObjectivesModel> showObjectives_By_Section_Role(int section_id,
			int role_id);

}
