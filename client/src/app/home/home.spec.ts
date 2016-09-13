import {inject, TestBed, tick, fakeAsync} from "@angular/core/testing";
import {BaseRequestOptions, Http} from "@angular/http";
import {MockBackend} from "@angular/http/testing";
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

/*
 BaseRequestOptions,
 MockBackend,
 {
 provide: Http,
 deps: [MockBackend, BaseRequestOptions]
 },
 */

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
    // this is not ideal yet, as we need to know which args are passed to the Router and it is dependent on the
    // implementation detail that a Router is used; better to check whether the new URL contains the ':id' and 'voting'.
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/valueCompass', '1', 'voting']);
  })));
});
