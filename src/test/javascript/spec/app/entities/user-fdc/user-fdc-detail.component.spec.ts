import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterFdcTestModule } from '../../../test.module';
import { UserFdcDetailComponent } from 'app/entities/user-fdc/user-fdc-detail.component';
import { UserFdc } from 'app/shared/model/user-fdc.model';

describe('Component Tests', () => {
  describe('UserFdc Management Detail Component', () => {
    let comp: UserFdcDetailComponent;
    let fixture: ComponentFixture<UserFdcDetailComponent>;
    const route = ({ data: of({ userFdc: new UserFdc(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterFdcTestModule],
        declarations: [UserFdcDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserFdcDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserFdcDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userFdc on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userFdc).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
