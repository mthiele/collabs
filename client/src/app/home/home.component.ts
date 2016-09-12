import {Component, Renderer, ElementRef} from '@angular/core';
import {Router} from '@angular/router';

import {ValueCompassService} from "../valueCompass.service";
import {ValueCompass} from "../valueCompass";

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

  constructor(private router: Router,
              private valueCompassService: ValueCompassService) {
  }

  ngOnInit() {
    console.log('hello `Home` component');
  }

  createCompass() {
    console.log('submit: ' + this.newCompassName);
    this.valueCompassService.create(this.newCompassName).then((valueCompass: ValueCompass) => {
        console.log('new compass id: ' + valueCompass.id);
        this.gotoValueCompass(valueCompass.id);
      }
    );
  }

  gotoValueCompass(id: string): void {
    console.log('route to: ' + id);
    let link = ['/valueCompass', id];
    this.router.navigate(link);
  }

  ngAfterViewInit() {
    var eb = new EventBus('http://localhost:8080/eventbus');

    eb.onopen = function () {

      // set a handler to receive a message
      eb.registerHandler('yeah', function (error, message) {
        // console.log('received a message: ' + message);
      });
    }
  }

}
