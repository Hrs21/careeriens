package com.careeriens.core.models.beans;

import java.util.List;

public class NavItem {

    private String label;
    private String link;
    private List<DropdownItem> dropdownItems;

    public boolean isHasDropdown() {
        return dropdownItems != null && !dropdownItems.isEmpty();
    }

    // ── Getters / Setters ─────────────────────────────────────
    public String getLabel()                        { return label; }
    public void setLabel(String label)              { this.label = label; }
    public String getLink()                         { return link; }
    public void setLink(String link)                { this.link = link; }
    public List<DropdownItem> getDropdownItems()    { return dropdownItems; }
    public void setDropdownItems(List<DropdownItem> d) { this.dropdownItems = d; }

    // ── Level-1 Dropdown Item ─────────────────────────────────
    public static class DropdownItem {
        private String dropLabel;
        private String dropLink;
        private List<SubItem> subItems;

        public boolean isHasSubItems() {
            return subItems != null && !subItems.isEmpty();
        }

        public String getDropLabel()                    { return dropLabel; }
        public void setDropLabel(String l)              { this.dropLabel = l; }
        public String getDropLink()                     { return dropLink; }
        public void setDropLink(String l)               { this.dropLink = l; }
        public List<SubItem> getSubItems()              { return subItems; }
        public void setSubItems(List<SubItem> s)        { this.subItems = s; }
    }

    // ── Level-2 Sub Item (Right Panel) ────────────────────────
    public static class SubItem {
        private String subLabel;
        private String subLink;

        public String getSubLabel()                     { return subLabel; }
        public void setSubLabel(String l)               { this.subLabel = l; }
        public String getSubLink()                      { return subLink; }
        public void setSubLink(String l)                { this.subLink = l; }
    }
}