import {inject, TestBed} from "@angular/core/testing";
import {BaseRequestOptions, ConnectionBackend, Http} from "@angular/http";
import {MockBackend} from "@angular/http/testing";
import {Home} from "./home.component";
import {Router} from "@angular/router";
import {SpyLocation} from "@angular/common/testing";
import {ValueCompassService} from "../valueCompass.service";

// Load the implementations that should be tested

describe('Home', () => {
  // provide our implementations or mocks to the dependency injector
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      BaseRequestOptions,
      MockBackend,
      {
        provide: Http,
        useFactory: function(backend: ConnectionBackend, defaultOptions: BaseRequestOptions) {
          return new Http(backend, defaultOptions);
        },
        deps: [MockBackend, BaseRequestOptions]
      },
      Home,
      SpyLocation,
      {
        provide: Router,
        deps: [SpyLocation]
      },
      ValueCompassService
    ]
  }));

  it('should log ngOnInit', inject([ Home ], (home) => {
    spyOn(console, 'log');
    expect(console.log).not.toHaveBeenCalled();

    home.ngOnInit();
    expect(console.log).toHaveBeenCalled();
  }));

});
