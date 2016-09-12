import {Component, OnInit, Renderer, ElementRef} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';

import {ValueCompass} from "../valueCompass";

@Component({
  selector: 'value-compass',  // <value-compass></value-compass>
  templateUrl: 'valueCompass.template.html'
})
export class ValueCompassComponent implements OnInit {

  valueCompassId: string;

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.params.forEach((params: Params) => {
      let id = params['id'];
      console.log('showing value compass: ' + id);
      this.valueCompassId = id;
    });
  }
}
