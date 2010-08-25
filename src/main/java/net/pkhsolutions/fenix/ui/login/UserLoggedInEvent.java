/*
 * Copyright (c) 2010 The original author(s)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.pkhsolutions.fenix.ui.login;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.Authentication;

/**
 * TODO Document me!
 * 
 * @author petter
 *
 */
public class UserLoggedInEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2819159232773687159L;	
	
	public UserLoggedInEvent(Authentication source) {
		super(source);
	}

	public Authentication getAuthentication() {
		return (Authentication) getSource();
	}
}
