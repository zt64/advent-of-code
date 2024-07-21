
function read_lines() result(lines)
   Use, intrinsic :: iso_fortran_env, Only : iostat_end

   integer :: io, iostat
   character(:), allocatable :: lines(:)
   character(256) :: line

   open(newunit=io, file="./input/d16.txt", status="old", action="read")

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
end function read_lines

function calculate_energized_tiles(arg) result(retval)
   integer, intent(in) :: arg
   integer :: retval


end function calculate_energized_tiles


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

   lines = read_lines()

   close(io)


   deallocate(lines)
end program aoc
