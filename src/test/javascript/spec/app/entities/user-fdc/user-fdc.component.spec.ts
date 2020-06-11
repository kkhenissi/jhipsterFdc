import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterFdcTestModule } from '../../../test.module';
import { UserFdcComponent } from 'app/entities/user-fdc/user-fdc.component';
import { UserFdcService } from 'app/entities/user-fdc/user-fdc.service';
import { UserFdc } from 'app/shared/model/user-fdc.model';

describe('Component Tests', () => {
  describe('UserFdc Management Component', () => {
    let comp: UserFdcComponent;
    let fixture: ComponentFixture<UserFdcComponent>;
    let service: UserFdcService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterFdcTestModule],
        declarations: [UserFdcComponent],
      })
        .overrideTemplate(UserFdcComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserFdcComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserFdcService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserFdc(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userFdcs && comp.userFdcs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
