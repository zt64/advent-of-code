program aoc
   Use, intrinsic :: iso_fortran_env, Only : iostat_end
   implicit none
   integer :: io, iostat
   character(:), allocatable :: lines(:)
   character(256) :: line

   integer :: i, beam_x, beam_y = 0

   open(newunit=io, file="./input/d16.txt", status="old", action="read")

   allocate(character(len=len(line)) :: lines(0))

   do while(.true.)
      read(io, "(A)", iostat=iostat) line

      select case(iostat)
       case(0)
         lines = [lines, line]
       case(iostat_end)
         exit
       case default
         write(*, *) 'Error in reading file'
         stop
      end select
   end do

   close(io)

   subroutine calculate_energized_tiles(arg1,  arg2)
      integer, intent(in) :: arg1
      integer, intent(out) ::  arg2


   end subroutine calculate_energized_tiles

   deallocate(lines)
end program aoc
