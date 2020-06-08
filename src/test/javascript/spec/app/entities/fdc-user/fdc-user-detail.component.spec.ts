import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterFdcTestModule } from '../../../test.module';
import { FdcUserDetailComponent } from 'app/entities/fdc-user/fdc-user-detail.component';
import { FdcUser } from 'app/shared/model/fdc-user.model';

describe('Component Tests', () => {
  describe('FdcUser Management Detail Component', () => {
    let comp: FdcUserDetailComponent;
    let fixture: ComponentFixture<FdcUserDetailComponent>;
    const route = ({ data: of({ fdcUser: new FdcUser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterFdcTestModule],
        declarations: [FdcUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FdcUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FdcUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fdcUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fdcUser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
