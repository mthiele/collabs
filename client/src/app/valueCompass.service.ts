import {Injectable}    from '@angular/core';
import {Headers, Http} from '@angular/http';
import {ValueCompass} from "./valueCompass";

@Injectable()
export class ValueCompassService {
  private headers = new Headers({'Content-Type': 'application/json; charset=utf-8'});
  private valueCompassUrl = 'http://localhost:8080/api/valueCompass';  // URL to web api
  constructor(private http: Http) {
  }

  getValueCompass(id: number): Promise<ValueCompass> {
    return this.http.get(this.valueCompassUrl + '/' + id)
      .toPromise()
      .then(response => response.json().data as ValueCompass)
      .catch(this.handleError);
  }

  create(name: string): Promise<ValueCompass> {
    console.log('sending: ' + JSON.stringify({name: name}));
    return this.http
      .post(this.valueCompassUrl, JSON.stringify({name: name}), {headers: this.headers})
      .toPromise()
      .then(res => {
        console.log('received text: ' + res.text());
        var valueCompass = res.json() as ValueCompass;
        console.log('received valueCompass: ' + valueCompass);
        return valueCompass;
      })
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
