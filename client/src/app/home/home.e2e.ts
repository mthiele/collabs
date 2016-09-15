import elementTextContains = protractor.until.elementTextContains;
import elementIsVisible = webdriver.until.elementIsVisible;
import tagName = webdriver.By.tagName;
import Condition = protractor.until.Condition;
import elementLocated = protractor.until.elementLocated;
import id = webdriver.By.id;

describe('App', () => {

  beforeEach(() => {
    // change hash depending on router LocationStrategy
    browser.get('/#/home');
  });


  it('should have a title', () => {
    let subject = browser.getTitle();
    let result = 'Wertekompass für verteilte und agile Teams';
    expect(subject).toEqual(result);
  });

  it('should not accept empty names', () => {
    let newButton = element(by.css('#btnNewValueCompass'));
    expect(newButton.isPresent()).toBeTruthy();
    let nameInput = element(by.css('#name'));
    expect(nameInput.isPresent()).toBeTruthy();
    expect(nameInput.getAttribute('class')).toMatch('ng-invalid');
    expect(nameInput.getAttribute('class')).not.toMatch('ng-valid');
    expect(newButton.getAttribute('ng-reflect-disabled')).toEqual('true');
    nameInput.sendKeys('');
    expect(nameInput.getAttribute('class')).toMatch('ng-invalid');
    expect(nameInput.getAttribute('class')).not.toMatch('ng-valid');
    expect(newButton.getAttribute('ng-reflect-disabled')).toEqual('true');
    nameInput.sendKeys(' \t');
    expect(nameInput.getAttribute('class')).toMatch('ng-invalid');
    expect(nameInput.getAttribute('class')).not.toMatch('ng-valid');
    expect(newButton.getAttribute('ng-reflect-disabled')).toEqual('true');
    nameInput.sendKeys('new name');
    expect(nameInput.getAttribute('class')).not.toMatch('ng-invalid');
    expect(nameInput.getAttribute('class')).toMatch('ng-valid');
    expect(newButton.getAttribute('ng-reflect-disabled')).toEqual('false');
  });

  it('should create a new compass and route to coting site', () => {
    let nameInput = element(by.css('#name'));
    nameInput.sendKeys('new name');
    let newButton = element(by.css('#btnNewValueCompass'));
    newButton.click();

    browser.wait(protractor.until.elementLocated(by.id('voting-headline'))).then(a => {
      expect(browser.getCurrentUrl()).toMatch('.*valueCompass/.*/voting');
      expect(element(by.id('voting-headline')).getText()).toMatch('bla blubb');
    })
  });
});
