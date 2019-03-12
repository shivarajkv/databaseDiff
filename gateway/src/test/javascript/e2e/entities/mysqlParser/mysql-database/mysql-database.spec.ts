import { browser } from 'protractor';
import { NavBarPage } from './../../../page-objects/jhi-page-objects';
import { MysqlDatabaseComponentsPage, MysqlDatabaseUpdatePage } from './mysql-database.page-object';

describe('MysqlDatabase e2e test', () => {
    let navBarPage: NavBarPage;
    let mysqlDatabaseUpdatePage: MysqlDatabaseUpdatePage;
    let mysqlDatabaseComponentsPage: MysqlDatabaseComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load MysqlDatabases', () => {
        navBarPage.goToEntity('mysql-database');
        mysqlDatabaseComponentsPage = new MysqlDatabaseComponentsPage();
        expect(mysqlDatabaseComponentsPage.getTitle()).toMatch(/gatewayApp.mysqlParserMysqlDatabase.home.title/);
    });

    it('should load create MysqlDatabase page', () => {
        mysqlDatabaseComponentsPage.clickOnCreateButton();
        mysqlDatabaseUpdatePage = new MysqlDatabaseUpdatePage();
        expect(mysqlDatabaseUpdatePage.getPageTitle()).toMatch(/gatewayApp.mysqlParserMysqlDatabase.home.createOrEditLabel/);
        mysqlDatabaseUpdatePage.cancel();
    });

    it('should create and save MysqlDatabases', () => {
        mysqlDatabaseComponentsPage.clickOnCreateButton();
        mysqlDatabaseUpdatePage.setVersionInput('5');
        expect(mysqlDatabaseUpdatePage.getVersionInput()).toMatch('5');
        mysqlDatabaseUpdatePage.setTablesInput('tables');
        expect(mysqlDatabaseUpdatePage.getTablesInput()).toMatch('tables');
        mysqlDatabaseUpdatePage.save();
        expect(mysqlDatabaseUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
