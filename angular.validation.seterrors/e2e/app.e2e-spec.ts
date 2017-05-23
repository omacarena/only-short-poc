import { Angular.Validation.SeterrorsPage } from './app.po';

describe('angular.validation.seterrors App', () => {
  let page: Angular.Validation.SeterrorsPage;

  beforeEach(() => {
    page = new Angular.Validation.SeterrorsPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
