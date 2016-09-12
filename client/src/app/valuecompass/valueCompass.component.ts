import {Component, OnInit, Renderer, ElementRef} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';

import {ValueCompass} from "../valueCompass";
import {Subscription} from "rxjs";

@Component({
  selector: 'value-compass',  // <value-compass></value-compass>
  templateUrl: 'valueCompass.template.html'
})
export class ValueCompassComponent implements OnInit {

  private sub: Subscription;

  valueCompassId: string;

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      let id = params['id'];
      console.log('showing value compass: ' + id);
      this.valueCompassId = id;
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}
