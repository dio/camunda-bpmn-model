/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.xml.model.impl.type.child;

import org.camunda.bpm.xml.model.impl.instance.ModelElementInstanceImpl;
import org.camunda.bpm.xml.model.impl.type.ModelElementTypeImpl;
import org.camunda.bpm.xml.model.impl.util.ModelUtil;
import org.camunda.bpm.xml.model.instance.ModelElementInstance;
import org.camunda.bpm.xml.model.type.child.ChildElement;

/**
 * Represents a single Child Element (ie. maxOccurs = 1);
 *
 * @author Daniel Meyer
 *
 */
public class ChildElementImpl<T extends ModelElementInstance> extends NamedChildElementCollection<T> implements ChildElement<T> {

  private final Class<T> elementType;

  public ChildElementImpl(Class<T> elementType, String localName, String namespaceUri, ModelElementTypeImpl containingType) {
    super(localName, namespaceUri, containingType);
    this.elementType = elementType;
    this.maxOccurs = 1;
  }

  /** the add operation replaces the child */
  @Override
  void performAddOperation(ModelElementInstanceImpl modelElement, T e) {
    modelElement.setUniqueChildElementByNameNs(e);
  }

  public void setChild(ModelElementInstance element, T newChildElement) {
    performAddOperation((ModelElementInstanceImpl) element, newChildElement);
  }

  @SuppressWarnings("unchecked")
  public T getChild(ModelElementInstance element) {
    ModelElementInstanceImpl elementInstanceImpl = (ModelElementInstanceImpl)element;

    ModelElementInstance childElement = elementInstanceImpl.getUniqueChildElementByNameNs(localName, namespaceUri);
    if(childElement != null) {
      ModelUtil.ensureInstanceOf(childElement, elementType);
      return (T) childElement;

    } else {
      return null;

    }
  }

}