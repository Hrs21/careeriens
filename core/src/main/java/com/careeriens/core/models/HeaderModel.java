package com.careeriens.core.models;

import com.careeriens.core.models.beans.NavItem;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class HeaderModel {

    // ── Branding ──────────────────────────────────────────────
    @ValueMapValue
    private String logoPath;

    @ValueMapValue
    private String logoAlt;

    @ValueMapValue
    private String brandNamePart1;

    @ValueMapValue
    private String brandNamePart2;

    @ValueMapValue
    private String homePagePath;

    // ── Navigation ────────────────────────────────────────────
    @SlingObject
    private Resource resource;

    private List<NavItem> navItems = new ArrayList<>();

    @PostConstruct
    protected void init() {
        // Parse the navItems multifield stored under ./navItems
        Resource navItemsResource = resource.getChild("navItems");
        if (navItemsResource != null) {
            for (Resource navChild : navItemsResource.getChildren()) {
                NavItem item = buildNavItem(navChild);
                navItems.add(item);
            }
        }
    }

    private NavItem buildNavItem(Resource navChild) {
        ValueMap vm = navChild.getValueMap();
        NavItem item = new NavItem();
        item.setLabel(vm.get("label", String.class));
        item.setLink(vm.get("link", String.class));

        // Parse dropdown items
        Resource dropdownResource = navChild.getChild("dropdownItems");
        if (dropdownResource != null) {
            List<NavItem.DropdownItem> dropItems = new ArrayList<>();
            for (Resource dropChild : dropdownResource.getChildren()) {
                ValueMap dvm = dropChild.getValueMap();
                NavItem.DropdownItem dropItem = new NavItem.DropdownItem();
                dropItem.setDropLabel(dvm.get("dropLabel", String.class));
                dropItem.setDropLink(dvm.get("dropLink", String.class));

                // Parse sub items (right panel)
                Resource subItemsResource = dropChild.getChild("subItems");
                if (subItemsResource != null) {
                    List<NavItem.SubItem> subItems = new ArrayList<>();
                    for (Resource subChild : subItemsResource.getChildren()) {
                        ValueMap svm = subChild.getValueMap();
                        NavItem.SubItem subItem = new NavItem.SubItem();
                        subItem.setSubLabel(svm.get("subLabel", String.class));
                        subItem.setSubLink(svm.get("subLink", String.class));
                        subItems.add(subItem);
                    }
                    dropItem.setSubItems(subItems);
                }
                dropItems.add(dropItem);
            }
            item.setDropdownItems(dropItems);
        }
        return item;
    }

    // ── Getters ───────────────────────────────────────────────
    public String getLogoPath()       { return logoPath; }
    public String getLogoAlt()        { return logoAlt != null ? logoAlt : "Logo"; }
    public String getBrandNamePart1() { return brandNamePart1; }
    public String getBrandNamePart2() { return brandNamePart2; }
    public String getHomePagePath()   { return homePagePath != null ? homePagePath : "/"; }
    public List<NavItem> getNavItems(){ return navItems; }
}