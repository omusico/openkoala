package org.openkoala.security.facade.impl.assembler;

import org.openkoala.security.core.domain.MenuResource;
import org.openkoala.security.facade.command.CreateChildMenuResourceCommand;
import org.openkoala.security.facade.command.CreateMenuResourceCommand;

public class MenuResourceAssembler {

	public static MenuResource toMenuResource(CreateMenuResourceCommand command){
		MenuResource result = new MenuResource(command.getName());
		result.setUrl(command.getUrl());
		result.setMenuIcon(command.getMenuIcon());
		result.setDescription(command.getDescription());
		return result;
	}
	
	public static MenuResource toMenuResource(CreateChildMenuResourceCommand command){
		MenuResource result = new MenuResource(command.getName());
		result.setUrl(command.getUrl());
		result.setMenuIcon(command.getMenuIcon());
		result.setDescription(command.getDescription());
		return result;
	}
	
}