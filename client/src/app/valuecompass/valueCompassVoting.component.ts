import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {Subscription} from 'rxjs';
import {Voting} from "../model/voting";
import {Dimension} from "../model/dimension";

@Component({
  selector: 'value-compass-voting',  // <value-compass></value-compass>
  templateUrl: 'valueCompassVoting.template.html'
})
export class ValueCompassVotingComponent implements OnInit {
  valueCompassId: string;
  voting: Voting = new Voting(0, [
    new Dimension("Fokus", [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10], 0),
    new Dimension("Offenheit", [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10], 0),
    new Dimension("Verpflichtung", [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10], 0),
    new Dimension("Mut", [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10], 0),
    new Dimension("Respekt", [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10], 0)
  ]);

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

  onSubmit() {

  }
}
