import { element, by, promise, ElementFinder } from 'protractor';

export class MysqlDatabaseComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-mysql-database div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MysqlDatabaseUpdatePage {
    pageTitle = element(by.id('jhi-mysql-database-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    versionInput = element(by.id('field_version'));
    tablesInput = element(by.id('field_tables'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setVersionInput(version): promise.Promise<void> {
        return this.versionInput.sendKeys(version);
    }

    getVersionInput() {
        return this.versionInput.getAttribute('value');
    }

    setTablesInput(tables): promise.Promise<void> {
        return this.tablesInput.sendKeys(tables);
    }

    getTablesInput() {
        return this.tablesInput.getAttribute('value');
    }

    save(): promise.Promise<void> {
        return this.saveButton.click();
    }

    cancel(): promise.Promise<void> {
        return this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}
