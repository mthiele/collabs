import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Subscription } from 'rxjs';

@Component({
  selector: 'value-compass-voting',  // <value-compass></value-compass>
  templateUrl: 'valueCompassVoting.template.html'
})
export class ValueCompassVotingComponent implements OnInit {
  valueCompassId: string;

  private sub: Subscription;

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
