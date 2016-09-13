import {inject, TestBed, tick, fakeAsync, resetFakeAsyncZone} from "@angular/core/testing";
import {Home} from "./home.component";
import {Router} from "@angular/router";
import {ValueCompassService} from "../valueCompass.service";
import {ValueCompass} from "../valueCompass";
import {Observable} from "rxjs";
import anything = jasmine.anything;

class MockValueCompassService {
  create(name: string): Promise<ValueCompass> {
    console.log('mockedValueCompassService.create: ' + name);
    return Observable.of(new ValueCompass('1', name)).toPromise();
  }
}
var service = new MockValueCompassService();

class MockRouter {
  navigate = jasmine.createSpy('navigate');
}
var mockRouter = new MockRouter();

describe('Home', () => {
  // provide our implementations or mocks to the dependency injector
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        Home,
        {
          provide: Router,
          useValue: mockRouter,
        },
        {
          provide: ValueCompassService,
          useValue: service
        }
      ]
    });
    resetFakeAsyncZone();
  });

  it('should log ngOnInit', inject([Home], (home) => {
    spyOn(console, 'log');
    expect(console.log).not.toHaveBeenCalled();

    home.ngOnInit();
    expect(console.log).toHaveBeenCalled();
  }));

  it('should navigate to value compass', fakeAsync(inject([Home], (home) => {
    home.newCompassName = 'Hello';
    home.createCompass();
    tick();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/valueCompass', '1', 'voting']);
  })));
});
