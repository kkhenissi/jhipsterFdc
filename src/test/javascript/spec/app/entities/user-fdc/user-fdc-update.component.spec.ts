import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterFdcTestModule } from '../../../test.module';
import { UserFdcUpdateComponent } from 'app/entities/user-fdc/user-fdc-update.component';
import { UserFdcService } from 'app/entities/user-fdc/user-fdc.service';
import { UserFdc } from 'app/shared/model/user-fdc.model';

describe('Component Tests', () => {
  describe('UserFdc Management Update Component', () => {
    let comp: UserFdcUpdateComponent;
    let fixture: ComponentFixture<UserFdcUpdateComponent>;
    let service: UserFdcService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterFdcTestModule],
        declarations: [UserFdcUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserFdcUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserFdcUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserFdcService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserFdc(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserFdc();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
