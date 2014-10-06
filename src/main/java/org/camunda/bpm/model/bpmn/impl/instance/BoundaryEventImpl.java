/* Licensed under the Apache License, Version 2.0 (the "License");
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

package org.camunda.bpm.model.bpmn.impl.instance;

import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.instance.Activity;
import org.camunda.bpm.model.bpmn.instance.BoundaryEvent;
import org.camunda.bpm.model.bpmn.instance.CatchEvent;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.impl.util.ModelTypeException;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.xml.type.attribute.Attribute;
import org.camunda.bpm.model.xml.type.reference.AttributeReference;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.*;
import static org.camunda.bpm.model.xml.type.ModelElementTypeBuilder.ModelTypeInstanceProvider;

/**
 * The BPMN boundaryEvent element
 *
 * @author Sebastian Menski
 */
public class BoundaryEventImpl extends CatchEventImpl implements BoundaryEvent {

  protected static Attribute<Boolean> cancelActivityAttribute;
  protected static AttributeReference<Activity> attachedToRefAttribute;

  public static void registerType(ModelBuilder modelBuilder) {
    ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(BoundaryEvent.class, BPMN_ELEMENT_BOUNDARY_EVENT)
      .namespaceUri(BPMN20_NS)
      .extendsType(CatchEvent.class)
      .instanceProvider(new ModelTypeInstanceProvider<BoundaryEvent>() {
        public BoundaryEvent newInstance(ModelTypeInstanceContext instanceContext) {
          return new BoundaryEventImpl(instanceContext);
        }
      });

    cancelActivityAttribute = typeBuilder.booleanAttribute(BPMN_ATTRIBUTE_CANCEL_ACTIVITY)
      .defaultValue(true)
      .build();

    attachedToRefAttribute = typeBuilder.stringAttribute(BPMN_ATTRIBUTE_ATTACHED_TO_REF)
      .required()
      .qNameAttributeReference(Activity.class)
      .build();

    typeBuilder.build();
  }

  public BoundaryEventImpl(ModelTypeInstanceContext context) {
    super(context);
  }

  @SuppressWarnings("rawtypes")
  public AbstractFlowNodeBuilder builder() {
    throw new ModelTypeException("Builder is not supported for type " + getClass());
  }

  public boolean cancelActivity() {
    return cancelActivityAttribute.getValue(this);
  }

  public void setCancelActivity(boolean cancelActivity) {
    cancelActivityAttribute.setValue(this, cancelActivity);
  }

  public Activity getAttachedTo() {
    return attachedToRefAttribute.getReferenceTargetElement(this);
  }

  public void setAttachedTo(Activity attachedTo) {
    attachedToRefAttribute.setReferenceTargetElement(this, attachedTo);
  }

  /** Camunda Attributes */

  @Override
  public boolean isCamundaAsyncAfter() {
    throw new UnsupportedOperationException("'asyncAfter' is not supported for 'Boundary Events' right now.");
  }

  @Override
  public void setCamundaAsyncAfter(boolean isCamundaAsyncAfter) {
    throw new UnsupportedOperationException("'asyncAfter' is not supported for 'Boundary Events' right now.");
  }

  @Override
  public boolean isCamundaAsyncBefore() {
    throw new UnsupportedOperationException("'asyncBefore' is not supported for 'Boundary Events' right now.");
  }

  @Override
  public void setCamundaAsyncBefore(boolean isCamundaAsyncBefore) {
    throw new UnsupportedOperationException("'asyncBefore' is not supported for 'Boundary Events' right now.");
  }

  @Override
  public boolean isCamundaExclusive() {
    throw new UnsupportedOperationException("'exclusive' is not supported for 'Boundary Events' right now.");
  }

  @Override
  public void setCamundaExclusive(boolean isCamundaExclusive) {
    throw new UnsupportedOperationException("'exclusive' is not supported for 'Boundary Events' right now.");
  }
}
