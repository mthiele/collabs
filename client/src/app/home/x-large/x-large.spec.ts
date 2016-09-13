import {fakeAsync, tick, TestBed, resetFakeAsyncZone} from "@angular/core/testing";
import {Component} from "@angular/core";
import {By} from "@angular/platform-browser/src/dom/debug/by";
import {XLarge} from "./x-large.directive";

// Load the implementations that should be tested

describe('x-large directive', () => {
  // Create a test component to test directives
  @Component({
    template: '<div x-large>Content</div>'
  })
  class TestComponent { }

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        XLarge,
        TestComponent
      ]
    });
    resetFakeAsyncZone();
  });

  it('should sent font-size to x-large', fakeAsync(() => {
    TestBed.compileComponents().then(() => {

      const fixture = TestBed.createComponent(TestComponent);
      fixture.detectChanges();
      tick();
      const element = fixture.debugElement.query(By.css('div'));

      expect(element.nativeElement.style.fontSize).toBe('x-large');

    });
  }));

});
