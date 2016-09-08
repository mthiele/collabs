import {Component, Renderer, ElementRef} from '@angular/core';

import { AppState } from '../app.service';
import { Title } from './title';
import { XLarge } from './x-large';

interface EventBus {
  onopen: () => void;
  registerHandler: (topic: string, callback: (err: string, msg: string) => void) => void;
}

declare var EventBus: {
  new (m: string): EventBus;
}

@Component({
  // The selector is what angular internally uses
  // for `document.querySelectorAll(selector)` in our index.html
  // where, in this case, selector is the string 'home'
  selector: 'home',  // <home></home>
  // We need to tell Angular's Dependency Injection which providers are in our app.
  providers: [
    Title
  ],
  // Our list of styles in our component. We may add more to compose many styles together
  styleUrls: [ './home.style.css' ],
  // Every Angular template is first compiled by the browser before Angular runs it's compiler
  templateUrl: './home.template.html'
})
export class Home {
  // Set our default values
  localState = { value: '' };
  // TypeScript public modifiers
  constructor(public appState: AppState, public title: Title) {

  }

  ngOnInit() {
    console.log('hello `Home` component');
    // this.title.getData().subscribe(data => this.data = data);
  }

  ngAfterViewInit() {
    var eb = new EventBus('http://localhost:8080/eventbus');

    eb.onopen = function() {

      // set a handler to receive a message
      eb.registerHandler('yeah', function (error, message) {
        console.log('received a message: ' + message/*.body*/);
      });
    }
  }

  submitState(value) {
    console.log('submitState', value);
    this.appState.set('value', value);
    this.localState.value = '';
  }
}
