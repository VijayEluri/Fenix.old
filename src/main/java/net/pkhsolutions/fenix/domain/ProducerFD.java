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
package net.pkhsolutions.fenix.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

/**
 * 
 * @author petter
 *
 */
@Entity
public class ProducerFD extends AbstractEntity {

	private static final long serialVersionUID = -4456335648174240246L;

	@OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
	private LocalizedString name = new LocalizedString();
	
	public LocalizedString getName() {
		return name;
	}
	
	// TODO Add units
}