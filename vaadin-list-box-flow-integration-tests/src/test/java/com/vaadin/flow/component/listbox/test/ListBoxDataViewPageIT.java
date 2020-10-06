/*
 * Copyright 2000-2020 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.vaadin.flow.component.listbox.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.flow.testutil.AbstractComponentIT;
import com.vaadin.flow.testutil.TestPath;

import static com.vaadin.flow.component.listbox.test.ListBoxDataViewPage.CURRENT_ITEM_SPAN;
import static com.vaadin.flow.component.listbox.test.ListBoxDataViewPage.DATA_VIEW_UPDATE_BUTTON;
import static com.vaadin.flow.component.listbox.test.ListBoxDataViewPage.HAS_NEXT_ITEM_SPAN;
import static com.vaadin.flow.component.listbox.test.ListBoxDataViewPage.HAS_PREV_ITEM_SPAN;
import static com.vaadin.flow.component.listbox.test.ListBoxDataViewPage.LIST_DATA_VIEW_ADD_BUTTON;
import static com.vaadin.flow.component.listbox.test.ListBoxDataViewPage.LIST_DATA_VIEW_ADD_FILTER_BUTTON;
import static com.vaadin.flow.component.listbox.test.ListBoxDataViewPage.LIST_DATA_VIEW_NEXT_BUTTON;
import static com.vaadin.flow.component.listbox.test.ListBoxDataViewPage.LIST_DATA_VIEW_PREV_BUTTON;
import static com.vaadin.flow.component.listbox.test.ListBoxDataViewPage.LIST_DATA_VIEW_REMOVE_BUTTON;
import static com.vaadin.flow.component.listbox.test.ListBoxDataViewPage.LIST_DATA_VIEW_REMOVE_FILTER_BUTTON;
import static com.vaadin.flow.component.listbox.test.ListBoxDataViewPage.LIST_DATA_VIEW_SET_FILTER_BUTTON;
import static com.vaadin.flow.component.listbox.test.ListBoxDataViewPage.LIST_DATA_VIEW_SORT_BUTTON;
import static com.vaadin.flow.component.listbox.test.ListBoxDataViewPage.LIST_DATA_VIEW_UPDATE_BUTTON;
import static com.vaadin.flow.component.listbox.test.ListBoxDataViewPage.LIST_BOX_FOR_ADD_TO_DATA_VIEW;
import static com.vaadin.flow.component.listbox.test.ListBoxDataViewPage.LIST_BOX_FOR_DATA_VIEW;
import static com.vaadin.flow.component.listbox.test.ListBoxDataViewPage.LIST_BOX_FOR_FILTER_DATA_VIEW;
import static com.vaadin.flow.component.listbox.test.ListBoxDataViewPage.LIST_BOX_FOR_LIST_DATA_VIEW;
import static com.vaadin.flow.component.listbox.test.ListBoxDataViewPage.LIST_BOX_FOR_REMOVE_FROM_DATA_VIEW;
import static com.vaadin.flow.component.listbox.test.ListBoxDataViewPage.LIST_BOX_FOR_SORT_DATA_VIEW;

@TestPath("list-box-data-view")
public class ListBoxDataViewPageIT extends AbstractComponentIT {

    private static final String CHANGED_1 = "changed-1";
    private static final String VAADIN_ITEM = "vaadin-item";
    private static final String FIRST = "first";
    private static final String SECOND = "second";

    private static boolean isOpened = false;

    @Before
    public void openPage() {

        if (isOpened)
            return;

        open();

        isOpened = true;
    }

    @Test
    public void testGenericDataView_refreshSingleItem_onlyReflectChangesOfThatItem() {

        findElement(By.id(DATA_VIEW_UPDATE_BUTTON)).click();

        WebElement group = findElement(By.id(LIST_BOX_FOR_DATA_VIEW));
        List<WebElement> buttons = group
                .findElements(By.tagName(VAADIN_ITEM));

        Assert.assertEquals("ListBox should have items", 2, buttons.size());

        Assert.assertEquals("First RadioButton should be updated to", CHANGED_1,
                buttons.get(0).getText());

        Assert.assertEquals(
                "Second RadioButton should still holds the old value", SECOND,
                buttons.get(1).getText());
    }

    @Test
    public void testListDataView_refreshSingleItem_onlyReflectChangesOfThatItem() {

        findElement(By.id(LIST_DATA_VIEW_UPDATE_BUTTON)).click();

        WebElement group = findElement(By.id(LIST_BOX_FOR_LIST_DATA_VIEW));
        List<WebElement> buttons = group
                .findElements(By.tagName(VAADIN_ITEM));

        Assert.assertEquals("ListBox should have items", 2, buttons.size());

        Assert.assertEquals("First RadioButton should be updated to", CHANGED_1,
                buttons.get(0).getText());

        Assert.assertEquals(
                "Second RadioButton should still holds the old value", SECOND,
                buttons.get(1).getText());
    }

    @Test
    public void testListDataView_addItem_shouldAddOneAndOnlyOneItem() {

        WebElement group = findElement(By.id(LIST_BOX_FOR_ADD_TO_DATA_VIEW));
        List<WebElement> buttons = group
                .findElements(By.tagName(VAADIN_ITEM));

        Assert.assertEquals("ListBox should have items", 1, buttons.size());

        findElement(By.id(LIST_DATA_VIEW_ADD_BUTTON)).click();

        waitForElementPresent(By.tagName(VAADIN_ITEM));

        group = findElement(By.id(LIST_BOX_FOR_ADD_TO_DATA_VIEW));
        buttons = group.findElements(By.tagName(VAADIN_ITEM));

        Assert.assertEquals("ListBox should have items", 2, buttons.size());

        Assert.assertEquals("First RadioButton should have the text", FIRST,
                buttons.get(0).getText());

        Assert.assertEquals("Second RadioButton should have the text", SECOND,
                buttons.get(1).getText());
    }

    @Test
    public void testListDataView_removeItem_shouldRemoveOneAndOnlyOneItem() {

        WebElement group = findElement(
                By.id(LIST_BOX_FOR_REMOVE_FROM_DATA_VIEW));
        List<WebElement> buttons = group
                .findElements(By.tagName(VAADIN_ITEM));

        Assert.assertEquals("ListBox should have items", 2, buttons.size());

        findElement(By.id(LIST_DATA_VIEW_REMOVE_BUTTON)).click();

        waitForElementPresent(By.tagName(VAADIN_ITEM));

        group = findElement(By.id(LIST_BOX_FOR_REMOVE_FROM_DATA_VIEW));
        buttons = group.findElements(By.tagName(VAADIN_ITEM));

        Assert.assertEquals("ListBox should have items", 1, buttons.size());

        Assert.assertEquals("First RadioButton should have the text", FIRST,
                buttons.get(0).getText());
    }

    @Test
    public void testListDataView_addAndRemoveFilters_shouldProduceCorrectNumberOfRadioButtons() {

        WebElement group = findElement(By.id(LIST_BOX_FOR_FILTER_DATA_VIEW));
        List<WebElement> buttons = group
                .findElements(By.tagName(VAADIN_ITEM));

        Assert.assertEquals("ListBox should have items", 10, buttons.size());

        findElement(By.id(LIST_DATA_VIEW_SET_FILTER_BUTTON)).click();
        waitForElementPresent(By.tagName(VAADIN_ITEM));

        group = findElement(By.id(LIST_BOX_FOR_FILTER_DATA_VIEW));
        buttons = group.findElements(By.tagName(VAADIN_ITEM));

        Assert.assertEquals("ListBox should have items", 5, buttons.size());

        findElement(By.id(LIST_DATA_VIEW_ADD_FILTER_BUTTON)).click();
        waitForElementPresent(By.tagName(VAADIN_ITEM));

        group = findElement(By.id(LIST_BOX_FOR_FILTER_DATA_VIEW));
        buttons = group.findElements(By.tagName(VAADIN_ITEM));

        Assert.assertEquals("ListBox should have items", 4, buttons.size());

        findElement(By.id(LIST_DATA_VIEW_REMOVE_FILTER_BUTTON)).click();
        waitForElementPresent(By.tagName(VAADIN_ITEM));

        group = findElement(By.id(LIST_BOX_FOR_FILTER_DATA_VIEW));
        buttons = group.findElements(By.tagName(VAADIN_ITEM));

        Assert.assertEquals("ListBox should have items", 10, buttons.size());
    }

    @Test
    public void testListDataView_nextAndPreviousItem_shouldComplyWithOriginalOrder() {

        WebElement currentItemSpan = findElement(By.id(CURRENT_ITEM_SPAN));
        WebElement hasNextItemSpan = findElement(By.id(HAS_NEXT_ITEM_SPAN));
        WebElement hasPrevItemSpan = findElement(By.id(HAS_PREV_ITEM_SPAN));

        Assert.assertEquals("Current Item's text should be", "second",
                currentItemSpan.getText());
        Assert.assertEquals("HasNextItem's text should be", "true",
                hasNextItemSpan.getText());
        Assert.assertEquals("HasPreviousItem's text should be", "true",
                hasPrevItemSpan.getText());

        findElement(By.id(LIST_DATA_VIEW_NEXT_BUTTON)).click();
        currentItemSpan = findElement(By.id(CURRENT_ITEM_SPAN));
        hasNextItemSpan = findElement(By.id(HAS_NEXT_ITEM_SPAN));
        hasPrevItemSpan = findElement(By.id(HAS_PREV_ITEM_SPAN));

        Assert.assertEquals("Current Item's text should be", "third",
                currentItemSpan.getText());
        Assert.assertEquals("HasNextItem's text should be", "false",
                hasNextItemSpan.getText());
        Assert.assertEquals("HasPreviousItem's text should be", "true",
                hasPrevItemSpan.getText());

        findElement(By.id(LIST_DATA_VIEW_PREV_BUTTON)).click();
        findElement(By.id(LIST_DATA_VIEW_PREV_BUTTON)).click();
        currentItemSpan = findElement(By.id(CURRENT_ITEM_SPAN));
        hasNextItemSpan = findElement(By.id(HAS_NEXT_ITEM_SPAN));
        hasPrevItemSpan = findElement(By.id(HAS_PREV_ITEM_SPAN));

        Assert.assertEquals("Current Item's text should be", "first",
                currentItemSpan.getText());
        Assert.assertEquals("HasNextItem's text should be", "true",
                hasNextItemSpan.getText());
        Assert.assertEquals("HasPreviousItem's text should be", "false",
                hasPrevItemSpan.getText());
    }

    @Test
    public void testListDataView_setSortComparator_shouldSortTheItems() {

        WebElement group = findElement(By.id(LIST_BOX_FOR_SORT_DATA_VIEW));
        List<WebElement> buttons = group
                .findElements(By.tagName(VAADIN_ITEM));

        Assert.assertEquals("first rendered radio button's text should be",
                "third", buttons.get(0).getText());
        Assert.assertEquals("second rendered radio button's text should be",
                "first", buttons.get(1).getText());
        Assert.assertEquals("third rendered radio button's text should be",
                "second", buttons.get(2).getText());

        findElement(By.id(LIST_DATA_VIEW_SORT_BUTTON)).click();
        waitForElementPresent(By.tagName(VAADIN_ITEM));

        group = findElement(By.id(LIST_BOX_FOR_SORT_DATA_VIEW));
        buttons = group.findElements(By.tagName(VAADIN_ITEM));

        Assert.assertEquals("first rendered radio button's text should be",
                "first", buttons.get(0).getText());
        Assert.assertEquals("second rendered radio button's text should be",
                "second", buttons.get(1).getText());
        Assert.assertEquals("third rendered radio button's text should be",
                "third", buttons.get(2).getText());
    }

}
