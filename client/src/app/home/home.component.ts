import {Component, Renderer, ElementRef} from '@angular/core';

import {XLarge} from './x-large';

interface EventBus {
  onopen: () => void;
  registerHandler: (topic: string, callback: (err: string, msg: string) => void) => void;
}

declare var EventBus: {
  new (m: string): EventBus;
}

@Component({
  selector: 'home',  // <home></home>
  styleUrls: ['./home.style.css'],
  templateUrl: './home.template.html'
})
export class Home {
  newCompassName: string;

  ngOnInit() {
    console.log('hello `Home` component');
    // this.title.getData().subscribe(data => this.data = data);
  }

  ngAfterViewInit() {
    var eb = new EventBus('http://localhost:8080/eventbus');

    eb.onopen = function () {

      // set a handler to receive a message
      eb.registerHandler('yeah', function (error, message) {
        console.log('received a message: ' + message);
      });
    }
  }

}
