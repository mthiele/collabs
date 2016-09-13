describe('App', () => {

  beforeEach(() => {
    // change hash depending on router LocationStrategy
    browser.get('/#/home');
  });


  it('should have a title', () => {
    let subject = browser.getTitle();
    let result  = 'Wertekompass für verteilte und agile Teams';
    expect(subject).toEqual(result);
  });

  it('should not accept empty names', () => {
    let newButton = element(by.css('#btnNewValueCompass'));
    expect(newButton.isPresent).toBeTruthy();
    let nameInput = element(by.css('#name'));
    expect(nameInput.isPresent).toBeTruthy();
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
});
