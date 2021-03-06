package gov.hhs.onc.sdcct.build.xml.jaxb.impl

import com.sun.codemodel.JFieldRef
import com.sun.xml.xsom.XSComplexType
import com.sun.xml.xsom.XSFacet
import com.sun.xml.xsom.XSRestrictionSimpleType
import com.sun.xml.xsom.XSSchemaSet
import com.sun.xml.xsom.XSSimpleType
import javax.annotation.Nullable
import org.apache.commons.collections4.IteratorUtils

class CodegenSchemaContext {
    private XSSchemaSet schemas
    private Map<String, XSComplexType> complexTypes
    private Map<String, XSSimpleType> simpleTypes
    private Map<String, XSRestrictionSimpleType> enumTypes
    private Map<String, JFieldRef> nsPrefixStaticRefModels
    private Map<String, JFieldRef> nsUriStaticRefModels
    
    void initialize(XSSchemaSet schemas) {
        this.complexTypes = IteratorUtils.toList((this.schemas = schemas).iterateComplexTypes()).collectEntries(new LinkedHashMap<>(),
            { Collections.singletonMap(it.name, it) })
        this.enumTypes = (this.simpleTypes = IteratorUtils.toList(this.schemas.iterateSimpleTypes()).collectEntries(new LinkedHashMap<>(),
            { Collections.singletonMap(it.name, it) })).findAll{ ((it.value instanceof XSRestrictionSimpleType) && !((XSRestrictionSimpleType) it.value)
            .getDeclaredFacets(XSFacet.FACET_ENUMERATION).isEmpty()) }.collectEntries(new LinkedHashMap<>(), { Collections.singletonMap(it.key, it.value) })
    }

    Map<String, XSComplexType> getComplexTypes() {
        return this.complexTypes
    }
    
    Map<String, XSRestrictionSimpleType> getEnumTypes() {
        return this.enumTypes
    }
    
    Map<String, JFieldRef> getNamespacePrefixStaticRefModels() {
        return this.nsPrefixStaticRefModels
    }
    
    void setNamespacePrefixStaticRefModels(Map<String, JFieldRef> nsPrefixStaticRefModels) {
        this.nsPrefixStaticRefModels = nsPrefixStaticRefModels
    }
    
    Map<String, JFieldRef> getNamespaceUriStaticRefModels() {
        return this.nsUriStaticRefModels
    }
    
    void setNamespaceUriStaticRefModels(Map<String, JFieldRef> nsUriStaticRefModels) {
        this.nsUriStaticRefModels = nsUriStaticRefModels
    }
    
    boolean hasSchemas() {
        return (this.schemas != null)
    }
    
    @Nullable
    XSSchemaSet getSchemas() {
        return this.schemas
    }
    
    Map<String, XSSimpleType> getSimpleTypes() {
        return this.simpleTypes
    }
}
