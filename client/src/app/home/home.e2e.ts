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

});
